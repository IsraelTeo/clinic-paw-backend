package com.clinic_paw.clinic_paw_backend.repository;

import com.clinic_paw.clinic_paw_backend.model.Employee;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleEmployeeRepository extends JpaRepository<EmployeeRoleEntity, Long>  {
}
