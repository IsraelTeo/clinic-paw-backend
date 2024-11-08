package com.clinic_paw.clinic_paw_backend.model;

import com.clinic_paw.clinic_paw_backend.enums.GenderPetEnum;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "first_name",
            nullable = false,
            length = 50,
            updatable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            length = 70,
            updatable = false,
            unique = true
    )
    private String lastName;

    @Column(length = 70)
    private String specie;

    @OneToOne(
            cascade = CascadeType.ALL,
            targetEntity = GenderPet.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "gender_id")
    private GenderPet gender;

    @Column(length = 50)
    private String race;

    private Integer age;

    private Double weight;

    @Column(length = 255)
    private String description;
}
