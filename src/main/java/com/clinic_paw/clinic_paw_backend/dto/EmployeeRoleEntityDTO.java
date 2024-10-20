package com.clinic_paw.clinic_paw_backend.dto;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmployeeRoleEntityDTO(Long id,

                                    @NotNull(message = "The role can not must be null.")
                                    EmployeeRoleEnum role) {
}
