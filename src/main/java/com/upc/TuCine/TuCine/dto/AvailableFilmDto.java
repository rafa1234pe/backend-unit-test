package com.upc.TuCine.TuCine.dto;

import com.upc.TuCine.TuCine.model.Business;
import com.upc.TuCine.TuCine.model.Film;
import com.upc.TuCine.TuCine.model.Promotion;
import lombok.Data;

@Data
public class AvailableFilmDto {
    private Integer id;
    private Business business;
    private Film film;
    private String customNotice;
    private Character isAvailable;
    private Promotion promotion;
}
