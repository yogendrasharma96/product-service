package com.pitara.product.entity;

import com.pitara.product.dto.ProductSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "products")
@Getter
@Setter
public class Product {

    @Id
    private String id;
    private String productId;
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

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;

}
