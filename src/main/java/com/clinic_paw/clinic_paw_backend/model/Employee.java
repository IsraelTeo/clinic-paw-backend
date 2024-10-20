package com.clinic_paw.clinic_paw_backend.model;

import com.clinic_paw.clinic_paw_backend.enums.EmployeeRoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String dni;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    private String direction;
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @ManyToOne(
            targetEntity = EmployeeRoleEntity.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "role_id")
    private EmployeeRoleEntity employeeRole;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private LocalDate createAt;
    @PrePersist
    public void prePersist(){
        this.createAt = LocalDate.now();
    }
}
