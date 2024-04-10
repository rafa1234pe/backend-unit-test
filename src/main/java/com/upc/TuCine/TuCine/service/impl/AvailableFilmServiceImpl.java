package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.AvailableFilmDto;
import com.upc.TuCine.TuCine.dto.BusinessDto;
import com.upc.TuCine.TuCine.model.AvailableFilm;
import com.upc.TuCine.TuCine.model.Business;
import com.upc.TuCine.TuCine.model.Film;
import com.upc.TuCine.TuCine.model.Promotion;
import com.upc.TuCine.TuCine.repository.AvailableFilmRepository;
import com.upc.TuCine.TuCine.repository.BusinessRepository;
import com.upc.TuCine.TuCine.repository.FilmRepository;
import com.upc.TuCine.TuCine.repository.PromotionRepository;
import com.upc.TuCine.TuCine.service.AvailableFilmService;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AvailableFilmServiceImpl implements AvailableFilmService {

    @Autowired
    private AvailableFilmRepository availableFilmRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ModelMapper modelMapper;

    AvailableFilmServiceImpl() { this.modelMapper = new ModelMapper(); }

    private AvailableFilmDto EntityToDto(AvailableFilm availableFilm) {
        return modelMapper.map(availableFilm, AvailableFilmDto.class);
    }

    private AvailableFilm DtoToEntity(AvailableFilmDto availableFilmDto) {
        return modelMapper.map(availableFilmDto, AvailableFilm.class);
    }

    @Override
    public List<AvailableFilmDto> getAllAvailableFilms() {
        List<AvailableFilm> availableFilms = availableFilmRepository.findAll();
        return availableFilms.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AvailableFilmDto createAvailableFilm(AvailableFilmDto availableFilmDto) {

        validateAvailableFilm(availableFilmDto);
        existsBusinessById(availableFilmDto.getBusiness().getId());
        existsFilmById(availableFilmDto.getFilm().getId());
        existsPromotionById(availableFilmDto.getPromotion().getId());

        Business business = businessRepository.findById(availableFilmDto.getBusiness().getId()).orElse(null);
        availableFilmDto.setBusiness(business);

        Film film = filmRepository.findById(availableFilmDto.getFilm().getId()).orElse(null);
        availableFilmDto.setFilm(film);

        Promotion promotion = promotionRepository.findById(availableFilmDto.getPromotion().getId()).orElse(null);
        availableFilmDto.setPromotion(promotion);

        AvailableFilm availableFilm = DtoToEntity(availableFilmDto);
        AvailableFilm createdAvailableFilm = availableFilmRepository.save(availableFilm);
        return EntityToDto(createdAvailableFilm);
    }

    @Override
    public AvailableFilmDto updateAvailableFilm(Integer id, AvailableFilmDto availableFilmDto) {
        AvailableFilm availableFilmToUpdate = availableFilmRepository.findById(id).orElse(null);
        if (availableFilmToUpdate == null) {
            return null;
        }

        validateAvailableFilm(availableFilmDto);
        existsBusinessById(availableFilmDto.getBusiness().getId());
        existsFilmById(availableFilmDto.getFilm().getId());
        existsPromotionById(availableFilmDto.getPromotion().getId());

        Business business = businessRepository.findById(availableFilmDto.getBusiness().getId()).orElse(null);
        availableFilmDto.setBusiness(business);

        Film film = filmRepository.findById(availableFilmDto.getFilm().getId()).orElse(null);
        availableFilmDto.setFilm(film);

        Promotion promotion = promotionRepository.findById(availableFilmDto.getPromotion().getId()).orElse(null);
        availableFilmDto.setPromotion(promotion);

        availableFilmToUpdate.setBusiness(availableFilmDto.getBusiness());
        availableFilmToUpdate.setFilm(availableFilmDto.getFilm());
        availableFilmToUpdate.setCustomNotice(availableFilmDto.getCustomNotice());
        availableFilmToUpdate.setIsAvailable(availableFilmDto.getIsAvailable());
        availableFilmToUpdate.setPromotion(availableFilmDto.getPromotion());

        AvailableFilm updatedAvailableFilm = availableFilmRepository.save(availableFilmToUpdate);
        return EntityToDto(updatedAvailableFilm);
    }

    @Override
    public AvailableFilmDto deleteAvailableFilm(Integer id) {
        AvailableFilm availableFilmToDelete = availableFilmRepository.findById(Integer.valueOf(id)).orElse(null);
        if (availableFilmToDelete == null) {
            return null;
        }
        availableFilmRepository.delete(availableFilmToDelete);
        return EntityToDto(availableFilmToDelete);
    }

    @Override
    public List<BusinessDto> getBusinessesByFilmId(Integer filmId) {
        List<AvailableFilm> availableFilms = availableFilmRepository.findAllByFilmId(filmId);
        return availableFilms.stream()
                .map(availableFilm -> modelMapper.map(availableFilm.getBusiness(), BusinessDto.class))
                .collect(Collectors.toList());
    }

    private void validateAvailableFilm(AvailableFilmDto availableFilmDto) {
        if (availableFilmDto.getBusiness() == null) {
            throw new ValidationException("Business id es requerido");
        }
        if (availableFilmDto.getFilm() == null) {
            throw new ValidationException("Film id es requerido");
        }
        if (availableFilmDto.getIsAvailable() == null) {
            throw new ValidationException("isAvailable es requerido");
        }
        if (availableFilmDto.getPromotion() == null) {
            throw new ValidationException("Promotion id es requerido");
        }
    }

    private void existsBusinessById(Integer id) {
        if (!businessRepository.existsById(id)) {
            throw new ValidationException("Business id not found");
        }
    }

    private void existsFilmById(Integer id) {
        if (!filmRepository.existsById(id)) {
            throw new ValidationException("Film id not found");
        }
    }

    private void existsPromotionById(Integer id) {
        if (!promotionRepository.existsById(id)) {
            throw new ValidationException("Promotion id not found");
        }
    }
}
