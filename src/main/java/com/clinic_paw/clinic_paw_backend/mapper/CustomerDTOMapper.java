package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.CustomerDTO;
import com.clinic_paw.clinic_paw_backend.model.Customer;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerDTOMapper extends Converter<CustomerDTO, Customer> {

    @Override
    Customer convert (CustomerDTO resource);
}
