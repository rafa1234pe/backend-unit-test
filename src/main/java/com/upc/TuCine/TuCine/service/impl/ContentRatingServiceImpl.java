package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.CategoryDto;
import com.upc.TuCine.TuCine.dto.ContentRatingDto;
import com.upc.TuCine.TuCine.dto.save.ContentRating.ContentRatingSaveDto;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;

import com.upc.TuCine.TuCine.model.ContentRating;
import com.upc.TuCine.TuCine.repository.ContentRatingRepository;
import com.upc.TuCine.TuCine.service.ContentRatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentRatingServiceImpl implements ContentRatingService {

    @Autowired
    private ContentRatingRepository contentRatingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ContentRatingServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    private ContentRatingDto EntityToDto(ContentRating contentRating){
        return modelMapper.map(contentRating, ContentRatingDto.class);
    }

    private ContentRating DtoToEntity(ContentRatingDto contentRatingDto){
        return modelMapper.map(contentRatingDto, ContentRating.class);
    }

    @Override
    public List<ContentRatingDto> getAllContentRatings() {
        List<ContentRating> contentRatings = contentRatingRepository.findAll();
        return contentRatings.stream()
                .map(this::EntityToDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public ContentRatingDto createContentRating(ContentRatingSaveDto contentRatingSaveDto) {

        ContentRatingDto contentRatingDto = modelMapper.map(contentRatingSaveDto, ContentRatingDto.class);

        validateContentRating(contentRatingDto);
        existsContentRatingByName(contentRatingDto.getName());

        ContentRating contentRating = DtoToEntity(contentRatingDto);
        return EntityToDto(contentRatingRepository.save(contentRating));
    }

    @Override
    public ContentRatingDto getContentRatingById(Integer id) {
        ContentRating contentRating = contentRatingRepository.findById(id).orElse(null);
        if (contentRating == null) {
            return null;
        }
        return EntityToDto(contentRating);
    }

    @Override
    public ContentRatingDto updateContentRating(Integer id, ContentRatingSaveDto contentRatingSaveDto) {
        ContentRatingDto contentRatingDto = modelMapper.map(contentRatingSaveDto, ContentRatingDto.class);
        ContentRatingDto contentRatingDto1 = getContentRatingById(id);
        if (contentRatingDto1 == null) {
            return null;
        }
        contentRatingDto.setName(contentRatingDto1.getName());
        contentRatingDto.setDescription(contentRatingDto1.getDescription());

        validateContentRating(contentRatingDto);
        ContentRating contentRating = DtoToEntity(contentRatingDto);
        return EntityToDto(contentRatingRepository.save(contentRating));
    }

    @Override
    public String deleteContentRating(Integer id) {

        ContentRating contentRating = contentRatingRepository.findById(id).orElseThrow(() -> new ValidationException("No existe la clasificacion"));
        contentRatingRepository.delete(contentRating);
        return "La clasificacion con nombre " + contentRating.getName() + " ha sido eliminada";
    }

    void validateContentRating(ContentRatingDto contentRating) {
        if (contentRating.getName() == null || contentRating.getName().isEmpty()) {
            throw new ValidationException("El nombre de la clasificacion es requerido");
        }
        if(contentRating.getDescription() == null || contentRating.getDescription().isEmpty()){
            throw new ValidationException("La descripcion de la clasificacion es requerida");
        }
    }

    void existsContentRatingByName(String name) {
        if (contentRatingRepository.existsContentRatingByName(name)) {
            throw new ValidationException("No se puede registrar la clasificación, ya existe uno con ese nombre");
        }
    }
}
