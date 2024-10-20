package com.clinic_paw.clinic_paw_backend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class PawException extends RuntimeException {

    private HttpStatus status;

    private String description;

    private List<String> reasons;

    public PawException(ApiError error) {
        this.status = error.getStatus();
        this.description = error.getMessage();
    }

}
