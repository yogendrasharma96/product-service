package com.pitara.product.exceptions;

import com.pitara.product.dto.ApiResponse;
import com.pitara.product.dto.ErrorDetail;
import com.pitara.product.dto.Errors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ApiResponse<String> response = new ApiResponse<>(null, new Errors(true, errorDetail));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidParameterException(InvalidParameterException ex) {
        String error = ex.getMessage().indexOf('.') != -1 ? ex.getMessage().substring(0, ex.getMessage().indexOf('.')) : ex.getMessage();
        ErrorDetail errorDetail = new ErrorDetail(error, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ApiResponse<String> response = new ApiResponse<>(null, new Errors(true, errorDetail));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().stream().findFirst().ifPresent(error -> {
                    String fieldName = error.getField();
                    String defaultMessage = error.getDefaultMessage();
                    errorMessage.append(fieldName)
                            .append(" - ")
                            .append(defaultMessage != null ? defaultMessage : "Invalid value");
                }
        );
        ErrorDetail errorDetail = new ErrorDetail(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        ApiResponse<String> response = new ApiResponse<>(null, new Errors(true, errorDetail));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        ErrorDetail errorDetail = new ErrorDetail(ex.getMessage().indexOf('(') != -1 ? ex.getMessage().substring(0, ex.getMessage().indexOf('(') - 1) : ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        ApiResponse<String> response = new ApiResponse<>(null, new Errors(true, errorDetail));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
