package com.clinic_paw.clinic_paw_backend.factory;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import com.clinic_paw.clinic_paw_backend.repository.IRoleEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeFactory implements IEmployeeFactory {

    private final IRoleEmployeeRepository roleEmployeeRepository;

    @Autowired
    public EmployeeFactory(IRoleEmployeeRepository roleEmployeeRepository) {
        this.roleEmployeeRepository = roleEmployeeRepository;
    }
    @Override
    public Employee createEmployee(Employee employee) {
        return switch (employee.getEmployeeRole().getRole()) {
            case VET -> this.creatingEmployeeWithRole(employee, EmployeeRoleEnum.VET);
            case RECEPTIONIST -> this.creatingEmployeeWithRole(employee, EmployeeRoleEnum.RECEPTIONIST);
            case ASSISTANT -> this.creatingEmployeeWithRole(employee, EmployeeRoleEnum.ASSISTANT);
        };
    }

    private Employee creatingEmployeeWithRole(Employee employee, EmployeeRoleEnum role) {
        Optional<EmployeeRoleEntity> roleEntityOptional = roleEmployeeRepository.findByRoleEnum(role);
        EmployeeRoleEntity employeeRole = roleEntityOptional.orElseGet(() -> enumRoleToEntity(role));
        return buildingEmployee(employee, employeeRole);
    }

    private Employee buildingEmployee(Employee employee, EmployeeRoleEntity employeeRole) {
        return Employee.builder()
                .dni(employee.getDni())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .birthDate(employee.getBirthDate())
                .phoneNumber(employee.getPhoneNumber())
                .direction(employee.getDirection())
                .employeeRole(employeeRole)
                .build();
    }

    private EmployeeRoleEntity enumRoleToEntity(EmployeeRoleEnum roleEnum) {
        return EmployeeRoleEntity.builder()
                .role(roleEnum)
                .build();
    }
}
