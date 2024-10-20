package com.clinic_paw.clinic_paw_backend.payload;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorResponse(String description, List<String>reasons) {
}
