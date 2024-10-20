package com.clinic_paw.clinic_paw_backend.exception;

import org.springframework.http.HttpStatus;

public enum ApiError {
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "Employee not found"),
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Argument not valid"),
    EMPLOYEE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Employee already exists"),
    CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Conversion failed"),
    ;

    private final HttpStatus status;

    private final String message;

    ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
