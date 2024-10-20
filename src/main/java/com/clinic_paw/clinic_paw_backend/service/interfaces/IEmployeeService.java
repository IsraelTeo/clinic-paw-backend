package com.clinic_paw.clinic_paw_backend.service.interfaces;

import com.clinic_paw.clinic_paw_backend.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeeService {

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO getEmployeeByDni(String dni);

    EmployeeDTO getEmployeeByEmail(String email);

    List<EmployeeDTO> getAllEmployees();

    void saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);
}
