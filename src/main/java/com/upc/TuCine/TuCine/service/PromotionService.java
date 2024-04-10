package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.PromotionDto;
import com.upc.TuCine.TuCine.dto.save.Promotion.PromotionSaveDto;
import com.upc.TuCine.TuCine.dto.save.Promotion.PromotionUpdateDto;

import java.util.List;

public interface PromotionService {
    List<PromotionDto> getAllPromotions();

    PromotionDto createPromotion(PromotionSaveDto promotionSaveDto);

    PromotionDto updatePromotion(Integer id, PromotionUpdateDto promotionUpdateDto);

    PromotionDto deletePromotion(Integer id);

}
