package com.pitara.product.service;

import com.pitara.product.dto.ApiResponse;
import com.pitara.product.dto.Errors;
import com.pitara.product.entity.ProductConfig;
import com.pitara.product.exceptions.CustomException;
import com.pitara.product.repository.ProductConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductDetailsService {

    private ProductConfigRepository productConfigRepository;


    public ApiResponse<ProductConfig> getProductListingOptions(String category) {

        ProductConfig productConfigs=productConfigRepository.findByType(category).orElse(null);

        return new ApiResponse<>(productConfigs, new Errors(false, null));

    }
}
