package com.pitara.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Errors {
    private boolean hasError;
    private ErrorDetail errorDetail;

}
