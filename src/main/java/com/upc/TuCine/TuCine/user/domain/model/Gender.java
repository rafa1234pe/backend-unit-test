package com.upc.TuCine.TuCine.user.domain.model;

import com.upc.TuCine.TuCine.user.domain.enumeration.Genders;
import jakarta.persistence.*;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //For connection
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 10, nullable = false)
    private Genders name;
}
