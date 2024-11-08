package com.clinic_paw.clinic_paw_backend.dto;

import com.clinic_paw.clinic_paw_backend.enums.GenderPetEnum;
import lombok.Builder;

@Builder
public record GenderPetDTO(Long id,
                           GenderPetEnum gender) {
}
