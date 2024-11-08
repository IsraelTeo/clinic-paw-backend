package com.clinic_paw.clinic_paw_backend.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "pet")
@ToString(exclude = "pet")
@Getter
@Setter
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String dni;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 70, unique = true)
    private String lastName;

    @Column(name = "phone_number", length = 20, unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Pet.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
