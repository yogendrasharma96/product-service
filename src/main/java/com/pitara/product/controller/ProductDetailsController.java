package com.pitara.product.controller;

import com.pitara.product.dto.ApiResponse;
import com.pitara.product.entity.ProductConfig;
import com.pitara.product.service.ProductDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/details")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductDetailsController {

    private ProductDetailsService productDetailsService;

    @GetMapping
    ApiResponse<ProductConfig> getProductListingOptions(@RequestParam String category) {
        return productDetailsService.getProductListingOptions(category);
    }
}
