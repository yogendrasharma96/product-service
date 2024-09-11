package com.pitara.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDetail {
    private String errorMsg;
    private int statusCode;
    private String statusValue;
}
