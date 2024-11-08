package com.clinic_paw.clinic_paw_backend.service.implementation;

import com.clinic_paw.clinic_paw_backend.dto.EmployeeDTO;
import com.clinic_paw.clinic_paw_backend.email.EmailContentMessage;
import com.clinic_paw.clinic_paw_backend.email.SendEmailService;
import com.clinic_paw.clinic_paw_backend.exception.ApiError;
import com.clinic_paw.clinic_paw_backend.exception.PawException;
import com.clinic_paw.clinic_paw_backend.factory.EmployeeFactory;
import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import com.clinic_paw.clinic_paw_backend.repository.IEmployeeRepository;
import com.clinic_paw.clinic_paw_backend.service.interfaces.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class EmployeeService implements IEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    private final IEmployeeRepository employeeRepository;

    private final EmployeeFactory employeeFactory;

    private final ConversionService conversionService;

    private final SendEmailService sendEmailService;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, EmployeeFactory employeeFactory, ConversionService conversionService,
                           SendEmailService sendEmailService) {
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.conversionService = conversionService;
        this.sendEmailService = sendEmailService;
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employee -> conversionService.convert(employee, EmployeeDTO.class))
                .orElseThrow(() -> {
                    LOGGER.warn("Employee with ID {} not found.", id);
                    return new PawException(ApiError.EMPLOYEE_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getEmployeeByDni(String dni) {
        return employeeRepository.findByEmployeeDni(dni)
                .map(employee -> conversionService.convert(employee, EmployeeDTO.class))
                .orElseThrow(() -> {
                    LOGGER.warn("Employee with dni {} not found.", dni);
                    return new PawException(ApiError.EMPLOYEE_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        return  employeeRepository.findByEmployeeEmail(email)
                .map(employee -> conversionService.convert(employee, EmployeeDTO.class))
                .orElseThrow(() -> {
                    LOGGER.warn("Employee with email {} not found.", email);
                    return new PawException(ApiError.EMPLOYEE_NOT_FOUND);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) {
            LOGGER.info("The employee list is empty.");
            return Collections.emptyList();
        }
        return employeeList.stream()
                .map(employee -> conversionService.convert(employee, EmployeeDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) {
       Optional<Employee> employeeOptional = employeeRepository.findById(employeeDTO.id());
       if (employeeOptional.isPresent()) {
           LOGGER.warn("Employee with ID {} already exists, cannot be saved.", employeeDTO.id());
           throw new PawException(ApiError.EMPLOYEE_ALREADY_EXISTS);
       }

       EmployeeRoleEntity roleEntitySave = conversionService.convert(employeeDTO.role(), EmployeeRoleEntity.class);
       Employee employeeSave = conversionService.convert(employeeDTO, Employee.class);
       assert employeeSave != null;

       employeeSave.setEmployeeRole(roleEntitySave);

       Employee employeeCreated = employeeFactory.createEmployee(employeeSave);
       employeeRepository.save(employeeCreated);
       sendEmail(employeeCreated);
    }

    @Transactional
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            LOGGER.warn("Employee with ID {} already exists, cannot be updated.", employeeDTO.id());
            throw new PawException(ApiError.EMPLOYEE_ALREADY_EXISTS);
        }
        Employee employee = conversionService.convert(employeeDTO, Employee.class);
        assert employee != null;
        updateEmployeeFields(employee, employeeDTO);

        employeeRepository.save(employee);

        return conversionService.convert(employee, EmployeeDTO.class);
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            LOGGER.warn("Employee not found with ID: {}.", id);
            throw new PawException(ApiError.EMPLOYEE_NOT_FOUND);
        }
        employeeRepository.deleteById(id);
    }

    private void updateEmployeeFields(Employee employee, EmployeeDTO employeeDTO) {
        employee.setEmployeeRole(employeeDTO.role());
        employee.setDirection(employeeDTO.direction());
        employee.setDni(employeeDTO.dni());
        employee.setEmail(employeeDTO.email());
        employee.setFirstName(employeeDTO.firstName());
        employee.setLastName(employeeDTO.lastName());
        employee.setPhoneNumber(employeeDTO.phoneNumber());
        employee.setBirthDate(employeeDTO.birthDate());
    }

    private void sendEmail(Employee employee){
        String subject = EmailContentMessage.getWelcomeEmailSubject();
        String messageBody = EmailContentMessage.getWelcomeEmailMessage(employee.getEmail());

        try {
            sendEmailService.sendEmail(employee.getEmail(), subject, messageBody);
        } catch (Exception e) {
            LOGGER.error("Error while sending email.", e);
        }
    }
}
