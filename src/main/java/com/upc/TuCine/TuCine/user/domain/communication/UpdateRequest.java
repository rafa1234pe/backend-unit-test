package com.upc.TuCine.TuCine.user.domain.communication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateRequest {
    @NotNull
    @NotBlank
    @Size(max=50)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max=60)
    private String lastName;

    @NotNull
    @NotBlank
    @Size(max=80)
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(max=9)
    private String phone;

    @NotNull
    @NotBlank
    @Size(max=8)
    private String dni;

    @NotNull
    @NotBlank
    @Size(max=100)
    private String password;

    @NotNull
    private LocalDate birthdate;

    private String address;

    private String imageSrc;
    private String bankAccount;

}
