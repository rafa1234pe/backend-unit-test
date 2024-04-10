package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.save.BusinessType.BusinessTypeDtoSave;
import com.upc.TuCine.TuCine.dto.save.BusinessType.BusinessTypeDtoSave;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import com.upc.TuCine.TuCine.model.BusinessType;
import com.upc.TuCine.TuCine.repository.BusinessTypeRepository;
import com.upc.TuCine.TuCine.service.BusinessTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    @Autowired
    private ModelMapper modelMapper;

    BusinessTypeServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    public BusinessTypeDto EntityToDto(BusinessType businessType){
        return modelMapper.map(businessType, BusinessTypeDto.class);
    }
    public BusinessType DtoToEntity(BusinessTypeDto businessTypeDto){
        return modelMapper.map(businessTypeDto, BusinessType.class);
    }
    @Override
    public List<BusinessTypeDto> getAllBusinessTypes() {
        List<BusinessType> businessTypes = businessTypeRepository.findAll();
        return businessTypes.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public BusinessTypeDto getBusinessTypeById(Integer id) {
        BusinessType businessType = businessTypeRepository.findById(id).orElse(null);
        if (businessType == null) {
            return null;
        }
        return EntityToDto(businessType);
    }

    @Override
    public BusinessTypeDto createBusinessType(BusinessTypeDtoSave businessTypeDtoSave) {

        BusinessTypeDto businessTypeDto = modelMapper.map(businessTypeDtoSave, BusinessTypeDto.class);

        validateBusinessType(businessTypeDto);
        existsBusinessTypeByName(businessTypeDto.getName());

        BusinessType businessType = DtoToEntity(businessTypeDto);
        return EntityToDto(businessTypeRepository.save(businessType));
    }
    void validateBusinessType(BusinessTypeDto businessType) {
        if (businessType.getName() == null || businessType.getName().isEmpty()) {
            throw new ValidationException("El nombre del tipo de negocio no puede estar vacío");
        }
    }

    void existsBusinessTypeByName(String name) {
        if (businessTypeRepository.existsBusinessTypeByName(name)) {
            throw new ValidationException("Ya hay un tipo de negocio que existe con este nombre");
        }
    }
}
