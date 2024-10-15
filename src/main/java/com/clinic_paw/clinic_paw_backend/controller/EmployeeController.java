package com.clinic_paw.clinic_paw_backend.controller;

import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.service.implementation.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee/{dni}")
    public ResponseEntity<?> getEmployeeByDni(@PathVariable("dni") String dni) {
        try{
            Employee employee = employeeService.getEmployeeByDni(dni);
            return ResponseEntity.ok(employee);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employees")
    public  ResponseEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();

        if(employeeList==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registrado correctamente.");
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id,@RequestBody Employee employee) {
        try{
            Employee employeeUpdated = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(employeeUpdated);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        try{
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Eliminado correctamente.");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
