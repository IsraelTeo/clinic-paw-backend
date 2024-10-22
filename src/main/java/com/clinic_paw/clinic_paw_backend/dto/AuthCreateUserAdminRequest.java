package com.clinic_paw.clinic_paw_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserAdminRequest(@NotBlank String username,
                                         @NotBlank String email,
                                         @NotBlank String password,
                                         @Valid AuthCreateRoleRequest roleRequest) {
}

