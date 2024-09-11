package com.pitara.product.controller;

import com.pitara.product.dto.ApiResponse;
import com.pitara.product.dto.ProductDTO;
import com.pitara.product.dto.ProductFilterDTO;
import com.pitara.product.entity.Product;
import com.pitara.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private ProductService productService;

    @PostMapping
    ApiResponse<String> addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @PostMapping(value = "/list")
    ApiResponse<Page<Product>> getFilterProduct(@RequestBody ProductFilterDTO productFilterDTO){
        return productService.getProducts(productFilterDTO);
    }


    @PutMapping
    ApiResponse<String> editProduct(@RequestBody ProductDTO product){
        return productService.editProduct(product);
    }

    @DeleteMapping
    ApiResponse<String> deleteProduct(@RequestParam String id){
        return productService.deleteProduct(id);
    }


}
