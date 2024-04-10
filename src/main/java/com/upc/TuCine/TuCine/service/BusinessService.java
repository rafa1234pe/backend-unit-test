package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.BusinessDto;
import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.ShowtimeDto;
import com.upc.TuCine.TuCine.dto.save.Business.BusinessSaveDto;

import java.util.List;

public interface BusinessService {

    BusinessDto createBusiness(BusinessSaveDto businessSaveDto);
    List<BusinessDto> getAllBusiness();
    BusinessDto getBusinessById(Integer id);

    BusinessDto updateBusiness(Integer id, BusinessSaveDto businessSaveDto);

    String deleteBusiness(Integer id);

    BusinessTypeDto getBusinessTypeByBusinessId(Integer id);

}
