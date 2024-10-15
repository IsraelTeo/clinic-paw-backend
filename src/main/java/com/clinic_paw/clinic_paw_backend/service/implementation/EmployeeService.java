package com.clinic_paw.clinic_paw_backend.service.implementation;

import com.clinic_paw.clinic_paw_backend.factory.EmployeeFactory;
import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import com.clinic_paw.clinic_paw_backend.repository.IEmployeeRepository;
import com.clinic_paw.clinic_paw_backend.repository.IRoleEmployeeRepository;
import com.clinic_paw.clinic_paw_backend.service.interfaces.IEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    private final EmployeeFactory employeeFactory;

    private final IRoleEmployeeRepository roleEmployeeRepository;

    @Transactional(readOnly = true)
    @Override
    public Employee getEmployeeByDni(String dni) {
        return employeeRepository.findByEmployeeDni(dni).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        if (employeeList.isEmpty()) {
            return null;
        }

        return employeeList;
    }

    @Transactional
    @Override
    public void saveEmployee(Employee employee) {
        Employee employeeSave = employeeFactory.createEmployee(employee);
        employeeRepository.save(employeeSave);
    }


    //CORREGIR
    @Transactional
    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee employeeUpdate = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeUpdate.setId(employee.getId());
        employeeUpdate.setDni(employee.getDni());
        employeeUpdate.setFirstName(employee.getFirstName());
        employeeUpdate.setLastName(employee.getLastName());
        employeeUpdate.setEmail(employee.getEmail());
        employeeUpdate.setEmployeeRole(employee.getEmployeeRole());
        employeeUpdate.setBirthDate(employee.getBirthDate());
        employeeUpdate.setPhoneNumber(employee.getPhoneNumber());

        employeeRepository.save(employeeUpdate);
        return employeeUpdate;
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Employee employeeDelete = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employeeRepository.delete(employeeDelete);
    }
}
