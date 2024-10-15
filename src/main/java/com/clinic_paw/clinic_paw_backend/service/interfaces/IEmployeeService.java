package com.clinic_paw.clinic_paw_backend.service.interfaces;

import com.clinic_paw.clinic_paw_backend.model.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee getEmployeeByDni(String dni);

    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);
}
