package com.upc.TuCine.TuCine.user.service;

import com.upc.TuCine.TuCine.user.resource.GenderDto;

import java.util.List;

public interface GenderService {
    void seed();
    List<GenderDto> getAllGenders();
}
