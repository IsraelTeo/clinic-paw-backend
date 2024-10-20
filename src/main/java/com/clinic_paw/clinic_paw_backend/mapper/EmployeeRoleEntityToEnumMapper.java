package com.clinic_paw.clinic_paw_backend.mapper;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface EmployeeRoleEntityToEnumMapper extends Converter<EmployeeRoleEntity, EmployeeRoleEnum> {

    @Override
    EmployeeRoleEnum convert(EmployeeRoleEntity resource);

}