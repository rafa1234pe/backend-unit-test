package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.save.BusinessType.BusinessTypeDtoSave;

import java.util.List;

public interface BusinessTypeService {

    List<BusinessTypeDto>getAllBusinessTypes();

    BusinessTypeDto getBusinessTypeById(Integer id);

    BusinessTypeDto createBusinessType(BusinessTypeDtoSave businessTypeDtoSave);

}
