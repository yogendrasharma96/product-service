package com.pitara.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse<T> {
    private T data;
    private Errors errors;
}
