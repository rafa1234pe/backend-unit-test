package com.upc.TuCine.TuCine.user.domain.model;

import com.upc.TuCine.TuCine.user.domain.enumeration.TypeUsers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_user")
public class TypeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //For connection
    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20, nullable = false)
    private TypeUsers name;
}

