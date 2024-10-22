package com.clinic_paw.clinic_paw_backend.repository;

import com.clinic_paw.clinic_paw_backend.enums.UserRoleEnum;
import com.clinic_paw.clinic_paw_backend.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query("SELECT r FROM UserRoleEntity r WHERE r.roleEnum IN :roleEnums")
    Set<UserRoleEntity> findRoleEntitiesByRoleEnumIn(@Param("roleEnums") List<String> roleEnums);

    @Query("SELECT r FROM UserRoleEntity r WHERE r.roleEnum = :roleEnum")
    Optional<UserRoleEntity> findByRoleEnum(@Param("roleEnum") UserRoleEnum roleEnum);
}