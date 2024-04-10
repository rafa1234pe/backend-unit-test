package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.BusinessDto;
import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.ShowtimeDto;

import com.upc.TuCine.TuCine.dto.save.Business.BusinessSaveDto;

import com.upc.TuCine.TuCine.dto.save.Business.BusinessSaveDto;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import com.upc.TuCine.TuCine.model.*;
import com.upc.TuCine.TuCine.repository.BusinessRepository;
import com.upc.TuCine.TuCine.repository.BusinessTypeRepository;
import com.upc.TuCine.TuCine.repository.ShowtimeRepository;
import com.upc.TuCine.TuCine.service.BusinessService;
import com.upc.TuCine.TuCine.user.domain.model.User;
import com.upc.TuCine.TuCine.user.persistence.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private ModelMapper modelMapper;

    BusinessServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    public BusinessDto EntityToDto(Business business){
        return modelMapper.map(business, BusinessDto.class);
    }

    public Business DtoToEntity(BusinessDto businessDto){
        return modelMapper.map(businessDto, Business.class);
    }

    public BusinessTypeDto convertBusinessTypeToDto(BusinessType businessType){
        return modelMapper.map(businessType, BusinessTypeDto.class);
    }

    @Override
    public BusinessDto createBusiness(BusinessSaveDto businessSaveDto) {


        BusinessDto businessDto = modelMapper.map(businessSaveDto, BusinessDto.class);

        validateBusiness(businessDto);
        existsByBusinessName(businessDto.getName());
        existsByBusinessRuc(businessDto.getRuc());

        User user = userRepository.findById(businessDto.getUser().getId()).orElse(null);
        businessDto.setUser(user);


        BusinessType businessType = businessTypeRepository.findById(businessDto.getBusinessType().getId()).orElse(null);
        businessDto.setBusinessType(businessType);


        Business business = DtoToEntity(businessDto);
        return EntityToDto(businessRepository.save(business));
    }

    @Override
    public List<BusinessDto> getAllBusiness() {
        List<Business> businesses = businessRepository.findAll();
        return businesses.stream()
                .map(this::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BusinessDto getBusinessById(Integer id) {
        Business business = businessRepository.findById(id).orElse(null);
        if (business == null) {
            return null;
        }
        return EntityToDto(business);
    }

    @Override
    public BusinessDto updateBusiness(Integer id, BusinessSaveDto businessSaveDto) {
        BusinessDto businessDto = modelMapper.map(businessSaveDto, BusinessDto.class);

        validateBusiness(businessDto);

        Business business = businessRepository.findById(id).orElse(null);
        if (business == null) {
            return null;
        }
        businessDto.setId(id);

        businessDto.setUser(userRepository.findById(businessSaveDto.getUser().getId()).orElse(null));

        businessDto.setBusinessType(businessTypeRepository.findById(businessSaveDto.getBusinessType().getId()).orElse(null));
        business = DtoToEntity(businessDto);
        return EntityToDto(businessRepository.save(business));
    }

    @Override
    public String deleteBusiness(Integer id) {
        Business business = businessRepository.findById(id).orElseThrow(() -> new ValidationException("No existe el negocio"));
        businessRepository.delete(business);
        return "El negocio con nombre " + business.getName() + " ha sido eliminado";
    }

    @Override
    public BusinessTypeDto getBusinessTypeByBusinessId(Integer id) {
        Business business = businessRepository.getById(id);
        if (business == null) {
            return null;
        }
        BusinessType businessType = business.getBusinessType();
        return convertBusinessTypeToDto(businessType);
    }



    public void validateBusiness(BusinessDto business) {
        if (business.getName() == null || business.getName().isEmpty()) {
            throw new ValidationException("El nombre de negocio es obligatorio");
        }
        if(business.getSocialReason()==null || business.getSocialReason().isEmpty()){
            throw new ValidationException("La razón social es obligatoria");
        }
        if (business.getRuc() == null || business.getRuc().isEmpty()) {
            throw new ValidationException("El RUC es obligatorio");
        }
        if (business.getPhone() == null || business.getPhone().isEmpty()) {
            throw new ValidationException("El teléfono es obligatorio");
        }
        if(business.getBusinessType()==null){
            throw new ValidationException("El tipo de negocio es obligatorio");
        }
        if(business.getUser()==null){
            throw new ValidationException("El dueño es obligatorio");
        }
    }

    public void existsByBusinessName(String businessName) {
        if (businessRepository.existsBusinessByName(businessName)) {
            throw new ValidationException("El nombre de negocio ya existe");
        }
    }
    public void existsByBusinessRuc(String businessRuc) {
        if (businessRepository.existsBusinessByRuc(businessRuc)) {
            throw new ValidationException("Un Business con ese RUC ya existe");
        }
    }

}
