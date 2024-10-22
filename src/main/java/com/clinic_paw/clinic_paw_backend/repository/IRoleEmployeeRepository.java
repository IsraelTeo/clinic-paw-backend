package com.clinic_paw.clinic_paw_backend.repository;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleEmployeeRepository extends JpaRepository<EmployeeRoleEntity, Long>  {

    @Query("SELECT r FROM EmployeeRoleEntity r WHERE r.role = :role")
    Optional<EmployeeRoleEntity> findByRoleEnum(@Param("role") EmployeeRoleEnum role);
}
