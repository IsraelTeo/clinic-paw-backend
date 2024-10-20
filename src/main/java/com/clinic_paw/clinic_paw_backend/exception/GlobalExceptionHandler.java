package com.clinic_paw.clinic_paw_backend.exception;

import com.clinic_paw.clinic_paw_backend.payload.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            reasons.add(String.format("%s - %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return ResponseEntity.status(ApiError.ARGUMENT_NOT_VALID.getStatus())
                .body(ErrorResponse.builder()
                .description(ApiError.ARGUMENT_NOT_VALID.getMessage())
                .reasons(reasons)
                .build());
    }
}
