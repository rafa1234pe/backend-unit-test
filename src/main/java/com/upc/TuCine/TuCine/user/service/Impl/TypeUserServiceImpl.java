package com.upc.TuCine.TuCine.user.service.Impl;

import com.upc.TuCine.TuCine.user.domain.enumeration.TypeUsers;
import com.upc.TuCine.TuCine.user.domain.model.TypeUser;
import com.upc.TuCine.TuCine.user.mapping.TypeUserMapper;
import com.upc.TuCine.TuCine.user.persistence.TypeUserRepository;
import com.upc.TuCine.TuCine.user.resource.TypeUserDto;
import com.upc.TuCine.TuCine.user.service.TypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TypeUserServiceImpl implements TypeUserService {

    @Autowired
    private TypeUserRepository typeUserRepository;
    private TypeUserMapper mapper;

    private static String[] DEFAULT_TYPE_USERS = { "CINEPHILE", "BUSINESS", "ADMIN" };

    public TypeUserServiceImpl(TypeUserMapper typeUserMapper) {
        this.mapper = typeUserMapper;
    }

    @Override
    public List<TypeUserDto> getAllTypeUsers() {
        return mapper.toResourceList(typeUserRepository.findAll());
    }

    @Override
    public void seed() {
        Arrays.stream(DEFAULT_TYPE_USERS).forEach(name -> {
            TypeUsers typeUserName = TypeUsers.valueOf(name);
            if (!typeUserRepository.existsByName(typeUserName)) {
                typeUserRepository.save((new TypeUser()).withName(typeUserName));
            }
        });
    }
}
