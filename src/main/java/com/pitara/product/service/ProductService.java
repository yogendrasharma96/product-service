package com.pitara.product.service;

import com.pitara.product.dto.*;
import com.pitara.product.entity.Product;
import com.pitara.product.exceptions.CustomException;
import com.pitara.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pitara.product.constants.Constants.CLOUD_FRONT_DEV;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final MongoTemplate mongoTemplate;

    private final ModelMapper modelMapper;

    private final S3Client s3Client;


    public ApiResponse<String> addProduct(ProductDTO productDTO) {
        Product product = createProducts(productDTO, new Product());
        productRepository.save(product);
        return new ApiResponse<>("Product SuccessFully Saved", new Errors(false, null));
    }


    private Product createProducts(ProductDTO productDTO, Product product) {
        modelMapper.map(productDTO, product);
        String generateUUIDNo = String.format("%06d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));
        product.setProductId(generateUUIDNo.substring( generateUUIDNo.length() - 6));
        product.setProductImages(getS3Url(productDTO.getProductImages(), getS3ImagePath(productDTO)));
        return product;
    }

    private String getS3ImagePath(ProductDTO dto) {
        return "product_images/" + dto.getProductCategory() + "/" + dto.getProductName();
    }

    private List<String> getS3Url(List<String> productImages, String path) {
        AtomicInteger i = new AtomicInteger();
        List<String> images = new ArrayList<>();
        for (String image : productImages) {
            byte[] decodedBytes = Base64.getDecoder().decode(image.split(",")[1]);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("pitara--dev")
                    .key(path + i.get())
                    .build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(ByteBuffer.wrap(decodedBytes)));
            images.add(CLOUD_FRONT_DEV + path + i.getAndIncrement());
        }
        return images;
    }

    public ApiResponse<Page<Product>> getProducts(ProductFilterDTO productFilterDTO) {
        Pageable pageable = PageRequest.of(
                productFilterDTO.getPageNo(),
                productFilterDTO.getPageSize(),
                Sort.by(productFilterDTO.getProductSort().equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "productSP"));
        Query query = new Query().with(pageable);

        if (!CollectionUtils.isEmpty(productFilterDTO.getProductColor())) {
            query.addCriteria(Criteria.where("productColor").in(productFilterDTO.getProductColor()));
        }
        if (!CollectionUtils.isEmpty(productFilterDTO.getProductFabric())) {
            query.addCriteria(Criteria.where("productFabric").in(productFilterDTO.getProductFabric()));
        }
        if (!CollectionUtils.isEmpty(productFilterDTO.getProductOccasion())) {
            query.addCriteria(Criteria.where("productOccasion").in(productFilterDTO.getProductOccasion()));
        }
        if (!CollectionUtils.isEmpty(productFilterDTO.getProductPattern())) {
            query.addCriteria(Criteria.where("productPattern").in(productFilterDTO.getProductPattern()));
        }
        if (!StringUtils.isEmpty(productFilterDTO.getProductPrice())) {
            String[] price = productFilterDTO.getProductPrice().split("-");
            query.addCriteria(Criteria.where("productSP").gte(Double.parseDouble(price[0].trim())).lte(Double.parseDouble(price[1].trim())));
        }

        List<Product> products = mongoTemplate.find(query, Product.class);
        if (!CollectionUtils.isEmpty(productFilterDTO.getProductSize())) {
            products = products.stream()
                    .filter(product -> product.getProductSizeQuantity().stream()
                            .anyMatch(sizeQuantity ->
                                    productFilterDTO.getProductSize().contains(sizeQuantity.getSize().toLowerCase())
                                            && sizeQuantity.getQuantity() > 0))
                    .collect(Collectors.toList());
        }
        long count = mongoTemplate.count(query, Product.class);
        return new ApiResponse<>(PageableExecutionUtils.getPage(products, pageable, () -> count), new Errors(false, null));

    }

    public ApiResponse<String> editProduct(ProductDTO productDTO) {
        Product getProduct = productRepository.findById(productDTO.getId()).orElseThrow(() -> new CustomException("Product not found"));
        modelMapper.map(productDTO, getProduct);
        productRepository.save(getProduct);
        return new ApiResponse<>("Product SuccessFully Updated", new Errors(false, null));
    }


    public ApiResponse<String> deleteProduct(String id) {
        productRepository.deleteById(id);
        return new ApiResponse<>("Product SuccessFully Deleted", new Errors(false, null));
    }
}
