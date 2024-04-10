package com.upc.TuCine.TuCine.user.resource;

import com.upc.TuCine.TuCine.user.domain.model.Gender;
import com.upc.TuCine.TuCine.user.domain.model.TypeUser;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String phone;
    private String email;
    private LocalDate createdAt;
    private String dni;
    private String password;
    private String imageSrc;
    private String bankAccount;

    private Gender gender;
    private TypeUser typeUser;
}

