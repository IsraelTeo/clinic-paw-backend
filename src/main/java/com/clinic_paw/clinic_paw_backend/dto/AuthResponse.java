package com.clinic_paw.clinic_paw_backend.dto;

import lombok.Builder;

@Builder
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status){
}
