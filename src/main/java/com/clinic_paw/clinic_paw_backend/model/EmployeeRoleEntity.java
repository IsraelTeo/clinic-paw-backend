package com.clinic_paw.clinic_paw_backend.model;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
public class EmployeeRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeRoleEnum role;
}
