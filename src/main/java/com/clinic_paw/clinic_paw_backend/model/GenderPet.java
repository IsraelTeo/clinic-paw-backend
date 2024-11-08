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
@Table(name = "genders")
public class GenderPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GenderPetEnum gender;
}
