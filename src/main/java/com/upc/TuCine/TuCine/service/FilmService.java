package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.*;
import com.upc.TuCine.TuCine.dto.save.Film.FilmSaveDto;
import com.upc.TuCine.TuCine.model.Showtime;

import java.util.List;

public interface FilmService {

    List<FilmDto>getAllFilms();

    FilmDto getFilmById(Integer id);

    FilmDto createFilm(FilmSaveDto filmSaveDto);

    FilmDto updateFilm(Integer id, FilmSaveDto filmSaveDto);

    String deleteFilm(Integer id);

    ContentRatingDto getContentRatingByFilmId(Integer id);

    List<CategoryDto>getAllCategoriesByFilmId(Integer id);

    List<ActorDto>getAllActorsByFilmId(Integer id);

    void addActorToFilm(Integer idFilm, Integer idActor);

    void addCategoryToFilm(Integer idFilm, Integer idCategory);


}
