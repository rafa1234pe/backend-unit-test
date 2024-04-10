package com.upc.TuCine.TuCine.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upc.TuCine.TuCine.model.AvailableFilm;
import com.upc.TuCine.TuCine.model.Business;
import com.upc.TuCine.TuCine.model.Film;
import com.upc.TuCine.TuCine.model.Promotion;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowtimeDto {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate playDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime playTime;
    private Integer capacity;
    private Float unitPrice;
    private AvailableFilm availableFilm;

}
