package com.pitara.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductFilterDTO {

    private List<String> productFabric;
    private List<String> productPattern;
    private List<String> productOccasion;
    private List<String> productSize;
    private List<String> productColor;
    private String productPrice;
    private String productSort;
    private Integer pageNo;
    private Integer pageSize=9;



}
