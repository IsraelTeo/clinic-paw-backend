package com.clinic_paw.clinic_paw_backend.controller;

import com.clinic_paw.clinic_paw_backend.dto.EmployeeDTO;
import com.clinic_paw.clinic_paw_backend.payload.MessageResponse;
import com.clinic_paw.clinic_paw_backend.service.implementation.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@Min(1) @PathVariable("id") Long id) {
        LOGGER.info("Getting employee by id {}.", id);
        EmployeeDTO response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee/{dni}")
    public ResponseEntity<EmployeeDTO> getEmployeeByDni(@PathVariable("dni") String dni) {
        LOGGER.info("Getting employee by dni: {}", dni);
        EmployeeDTO response = employeeService.getEmployeeByDni(dni);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@RequestParam(value = "email") String email) {
        LOGGER.info("Getting employee by email: {}", email);
        EmployeeDTO response = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO> > getAllEmployees() {
        LOGGER.info("Getting all employees.");
        List<EmployeeDTO> response = employeeService.getAllEmployees();
        if(response.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/employee")
    public ResponseEntity<MessageResponse> saveEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        LOGGER.info("Creating employee.");
        employeeService.saveEmployee(employeeDTO);
        MessageResponse response =MessageResponse.builder()
                .message("Employee created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@Min(1) @PathVariable("id") Long id, @RequestBody @Valid EmployeeDTO employeeDTO) {
        LOGGER.info("Updating employee with ID {}:", id);
        EmployeeDTO response = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<MessageResponse> deleteEmployee(@PathVariable("id") Long id) {
        LOGGER.info("Deleting employee with ID {}.", id);
        employeeService.deleteEmployee(id);
        MessageResponse response = MessageResponse.builder()
                .message("Employee deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
