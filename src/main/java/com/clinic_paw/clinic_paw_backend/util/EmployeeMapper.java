package com.clinic_paw.clinic_paw_backend.util;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;

public class EmployeeMapper {

    public static EmployeeRoleEnum entityToEnum(EmployeeRoleEntity roleEntity) {
        return roleEntity.getRole();
    }

    public static EmployeeRoleEntity enumToEntity(EmployeeRoleEnum roleEnum) {
        EmployeeRoleEntity roleEntity = new EmployeeRoleEntity();
        roleEntity.setRole(roleEnum);
        return roleEntity;
    }
}
