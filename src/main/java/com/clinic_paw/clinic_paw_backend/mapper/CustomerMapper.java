package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.CustomerDTO;
import com.clinic_paw.clinic_paw_backend.model.Customer;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CustomerMapper  extends Converter<Customer, CustomerDTO> {

    @Override
    CustomerDTO convert (Customer resource);
}
