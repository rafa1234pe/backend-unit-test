package com.upc.TuCine.TuCine.repository;

import com.upc.TuCine.TuCine.model.AvailableFilm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableFilmRepository extends JpaRepository<AvailableFilm, Integer> {
    List<AvailableFilm> findAllByFilmId(Integer filmId);
    AvailableFilm findByFilmIdAndBusinessId(Integer filmId, Integer businessId);
}
