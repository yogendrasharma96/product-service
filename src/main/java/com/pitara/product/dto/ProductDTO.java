package com.pitara.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductDTO {

    private String id;
    private String productName;
    private String productDescription;
    private String productCategory;
    private List<String> productTags;
    private List<String> productColor;
    private List<String> productFabric;
    private List<String> productPattern;
    private List<String> productOccasion;
    private List<String> productWashCare;
    private String productBlouse;
    private List<ProductSize> productSizeQuantity;
    private Double productMrp;
    private Double productSP;
    private String productShippingDays;
    private String productCoupon;
    private String productSale;
    private String productReturn;
    private List<String> productImages;
}
