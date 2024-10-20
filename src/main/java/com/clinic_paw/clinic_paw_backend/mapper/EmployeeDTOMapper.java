package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.EmployeeDTO;
import com.clinic_paw.clinic_paw_backend.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface EmployeeDTOMapper extends Converter<EmployeeDTO, Employee> {

    @Override
    Employee convert(EmployeeDTO resource);

}

