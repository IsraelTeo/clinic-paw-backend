package com.clinic_paw.clinic_paw_backend.factory;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactory implements IEmployeeFactory {

    private final ConversionService conversionService;

    @Autowired
    public EmployeeFactory(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    private Employee buildEmployee(Employee employee, EmployeeRoleEnum role) {
        return Employee.builder()
                .dni(employee.getDni())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .birthDate(employee.getBirthDate())
                .phoneNumber(employee.getPhoneNumber())
                .employeeRole(conversionService.convert(role, EmployeeRoleEntity.class))
                .build();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeRoleEnum roleEnum = employee.getEmployeeRole().getRole();

        return switch (roleEnum) {
            case VET -> this.buildEmployee(employee, EmployeeRoleEnum.VET);
            case RECEPTIONIST -> this.buildEmployee(employee, EmployeeRoleEnum.RECEPTIONIST);
            case ASSISTANT -> this.buildEmployee(employee, EmployeeRoleEnum.ASSISTANT);
        };
    }
}
