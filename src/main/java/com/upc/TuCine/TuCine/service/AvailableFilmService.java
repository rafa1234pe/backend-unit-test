package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.AvailableFilmDto;
import com.upc.TuCine.TuCine.dto.BusinessDto;

import java.util.List;

public interface AvailableFilmService {
    List<AvailableFilmDto> getAllAvailableFilms();
    AvailableFilmDto createAvailableFilm(AvailableFilmDto availableFilmDto);
    AvailableFilmDto updateAvailableFilm(Integer id, AvailableFilmDto availableFilmDto);
    AvailableFilmDto deleteAvailableFilm(Integer id);

    List<BusinessDto> getBusinessesByFilmId(Integer filmId);
}
