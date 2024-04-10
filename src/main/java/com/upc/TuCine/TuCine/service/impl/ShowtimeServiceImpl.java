package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.ShowtimeDto;
import com.upc.TuCine.TuCine.repository.*;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import com.upc.TuCine.TuCine.model.*;
import com.upc.TuCine.TuCine.service.ShowtimeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private AvailableFilmRepository availableFilmRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ShowtimeServiceImpl() {
        this.modelMapper = new ModelMapper();
    }

    private ShowtimeDto EntityToDto(Showtime showtime){
        return modelMapper.map(showtime, ShowtimeDto.class);
    }

    private Showtime DtoToEntity(ShowtimeDto showtimeDto){
        return modelMapper.map(showtimeDto, Showtime.class);
    }

    @Override
    public List<ShowtimeDto> getAllShowtimes() {
        List<Showtime> showtimes = showtimeRepository.findAll();
        return showtimes.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ShowtimeDto getShowtimeById(Integer id) {
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if (showtime == null) {
            return null; // O lanzar una excepción si lo prefieres
        }
        return EntityToDto(showtime);
    }

    @Override
    public ShowtimeDto createShowtime(ShowtimeDto showtimeDto) {

        validateShowtime(showtimeDto);

        AvailableFilm availableFilm;
        try {
            availableFilm = availableFilmRepository.findById(showtimeDto.getAvailableFilm().getId()).orElse(null);
        } catch (Exception e) {
            availableFilm = null;
        }
        showtimeDto.setAvailableFilm(availableFilm);
        Showtime showtime = DtoToEntity(showtimeDto);
        Showtime createdShowtime = showtimeRepository.save(showtime);

        return EntityToDto(createdShowtime);
    }

    @Override
    public ShowtimeDto updateShowtime(Integer id, ShowtimeDto showtimeDto) {
        Showtime showtimeToUpdate = showtimeRepository.findById(id).orElse(null);
        if (showtimeToUpdate == null) {
            return null; // O lanzar una excepción si lo prefieres
        }
        validateShowtime(showtimeDto);
        existsAvailableFilmById(showtimeDto.getAvailableFilm().getId());

        // Actualizar los campos del Showtime existente
        showtimeToUpdate.setAvailableFilm(showtimeDto.getAvailableFilm());
        showtimeToUpdate.setPlayDate(showtimeDto.getPlayDate());
        showtimeToUpdate.setPlayTime(showtimeDto.getPlayTime());
        showtimeToUpdate.setCapacity(showtimeDto.getCapacity());
        showtimeToUpdate.setUnitPrice(showtimeDto.getUnitPrice());

        // Guardar el Showtime actualizado en el repositorio
        Showtime updatedShowtime = showtimeRepository.save(showtimeToUpdate);

        return EntityToDto(updatedShowtime);
    }

    @Override
    public ShowtimeDto deleteShowtime(Integer id) {
        Showtime showtimeToDelete = showtimeRepository.findById(id).orElse(null);
        if (showtimeToDelete == null) {
            return null; // O lanzar una excepción si lo prefieres
        }
        showtimeRepository.delete(showtimeToDelete);
        return EntityToDto(showtimeToDelete);
    }

    @Override
    public List<ShowtimeDto> getShowtimesByFilmAndBusiness(Integer filmId, Integer businessId) {
        // Primero, verifica si existe una relación AvailableFilm para el Film y Business proporcionados
        AvailableFilm availableFilm = availableFilmRepository.findByFilmIdAndBusinessId(filmId, businessId);
        if (availableFilm == null) {
            return Collections.emptyList(); // No se encontró una relación entre Film y Business
        }

        // Si se encuentra la relación, obtén todos los showtimes asociados a esa AvailableFilm
        List<Showtime> showtimes = showtimeRepository.findAllByAvailableFilmId(availableFilm.getId());
        return showtimes.stream()
                .map(showtime -> modelMapper.map(showtime, ShowtimeDto.class))
                .collect(Collectors.toList());
    }

    private void validateShowtime(ShowtimeDto showtime) {
        if (showtime.getAvailableFilm() == null ) {
            throw new ValidationException("La película disponible es obligatoria");
        }
        if (showtime.getPlayDate() == null) {
            throw new ValidationException("La fecha es obligatoria");
        }
        if (showtime.getPlayTime() == null ) {
            throw new ValidationException("La hora es obligatoria");
        }
        if (showtime.getUnitPrice() == null ) {
            throw new ValidationException("El precio es obligatorio");
        }
    }

    private void existsAvailableFilmById(Integer id) {
        if (!availableFilmRepository.existsById(id)) {
            throw new ValidationException("La película no existe");
        }
    }
}
