package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.dto.EmployeeRoleEntityDTO;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface EmployeeRoleEntityMapper extends Converter<EmployeeRoleEntity, EmployeeRoleEntityDTO> {

    @Override
    EmployeeRoleEntityDTO convert(EmployeeRoleEntity resource);

}
