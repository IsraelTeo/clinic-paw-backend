package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.PetDTO;
import com.clinic_paw.clinic_paw_backend.model.Pet;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PetMapper extends Converter<Pet, PetDTO>{

    @Override
    PetDTO convert (Pet resource);
}