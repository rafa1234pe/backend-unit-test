package com.upc.TuCine.TuCine.user.service.Impl;

import com.upc.TuCine.TuCine.shared.mapping.EnhancedModelMapper;
import com.upc.TuCine.TuCine.user.domain.enumeration.Genders;
import com.upc.TuCine.TuCine.user.domain.model.Gender;
import com.upc.TuCine.TuCine.user.mapping.GenderMapper;
import com.upc.TuCine.TuCine.user.persistence.GenderRepository;
import com.upc.TuCine.TuCine.user.resource.GenderDto;
import com.upc.TuCine.TuCine.user.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {
    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private EnhancedModelMapper enhancedMapper;
    private GenderMapper mapper;

    private static String[] DEFAULT_GENDERS = { "MALE", "FEMALE", "OTHER" };

    GenderServiceImpl(GenderMapper genderMapper) {
        this.mapper = genderMapper;
    }

    @Override
    public List<GenderDto> getAllGenders() {
        return mapper.toResourceList(genderRepository.findAll());
    }

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_GENDERS).forEach(name -> {
            Genders genderName = Genders.valueOf(name);
            if (!genderRepository.existsByName(genderName)) {
                genderRepository.save(new Gender().withName(genderName));

            }
        });
    }
}
