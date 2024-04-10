package com.upc.TuCine.TuCine.user.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 80, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "phone", length = 9, nullable = false, unique = true)
    private String phone;

    @Column(name = "email", length = 80, nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name="dni", length = 8, nullable = false)
    private String dni;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "image_src")
    private String imageSrc;

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "address")
    private String address;

    //Relationships
    @ManyToOne
    @JoinColumn(name = "gender_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "type_user_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TypeUser typeUser;
}
