package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.GenderPetDTO;
import com.clinic_paw.clinic_paw_backend.model.GenderPet;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface GenderPetDTOMapper extends Converter<GenderPetDTO, GenderPet> {

    @Override
    GenderPet convert (GenderPetDTO resource);
}
