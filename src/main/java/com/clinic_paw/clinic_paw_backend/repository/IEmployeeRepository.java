package com.clinic_paw.clinic_paw_backend.repository;

import com.clinic_paw.clinic_paw_backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.dni = :dni")
    Optional<Employee> findByEmployeeDni(@Param("dni") String dni);

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmployeeEmail(@Param("email") String email);
}
