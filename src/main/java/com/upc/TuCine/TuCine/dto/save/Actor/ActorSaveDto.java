package com.upc.TuCine.TuCine.dto.save.Actor;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ActorSaveDto {

    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate birthday;
    private String profileSrc;

}
