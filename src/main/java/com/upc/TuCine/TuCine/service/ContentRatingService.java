package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.ContentRatingDto;
import com.upc.TuCine.TuCine.dto.save.ContentRating.ContentRatingSaveDto;

import java.util.List;

public interface ContentRatingService {

    List<ContentRatingDto> getAllContentRatings();

    ContentRatingDto createContentRating(ContentRatingSaveDto contentRatingSaveDto);

    ContentRatingDto getContentRatingById(Integer id);

    ContentRatingDto updateContentRating(Integer id, ContentRatingSaveDto contentRatingSaveDto);

    String deleteContentRating(Integer id);

}
