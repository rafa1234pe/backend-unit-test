package com.upc.TuCine.TuCine.user.service;

import com.upc.TuCine.TuCine.user.resource.TypeUserDto;

import java.util.List;

public interface TypeUserService {
    void seed();
    List<TypeUserDto> getAllTypeUsers();
}
