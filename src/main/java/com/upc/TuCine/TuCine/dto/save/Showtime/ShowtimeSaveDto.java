package com.upc.TuCine.TuCine.dto.save.Showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upc.TuCine.TuCine.model.Business;
import com.upc.TuCine.TuCine.model.Film;
import com.upc.TuCine.TuCine.model.Promotion;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowtimeSaveDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    private Float price;
    private ShowtimeFilmSaveDto film;
    private ShowtimeBusinessSaveDto business;
    private ShowtimePromotionSaveDto promotion;
}
