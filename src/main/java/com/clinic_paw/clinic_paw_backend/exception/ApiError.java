package com.clinic_paw.clinic_paw_backend.exception;

import org.springframework.http.HttpStatus;

public enum ApiError {
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "Employee not found"),
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Argument not valid"),
    EMPLOYEE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Employee already exists"),
    CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Conversion failed"),
    GET_ROLE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Get role failed"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "Incorrect password"),
    INCORRECT_USERNAME(HttpStatus.UNAUTHORIZED, "Incorrect username"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "Username not found"),
    BAD_CREDENTIALS(HttpStatus.BAD_REQUEST, "Bad credentials"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Role not found"),
    USER_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "User creation failed"),
    JSON_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Json processing failed"),
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
