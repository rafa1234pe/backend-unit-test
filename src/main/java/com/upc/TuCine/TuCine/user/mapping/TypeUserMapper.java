package com.upc.TuCine.TuCine.user.mapping;

import com.upc.TuCine.TuCine.shared.mapping.EnhancedModelMapper;
import com.upc.TuCine.TuCine.user.domain.enumeration.TypeUsers;
import com.upc.TuCine.TuCine.user.domain.model.TypeUser;
import com.upc.TuCine.TuCine.user.resource.TypeUserDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypeUserMapper {
    @Autowired
    EnhancedModelMapper mapper;

    Converter<TypeUsers, String> typeUserToString = new AbstractConverter<>() {
        @Override
        protected String convert(TypeUsers typeUser) {
            return typeUser == null ? null : typeUser.name();
        }
    };

    // Object Mapping
    public TypeUserDto toResource(TypeUser model) {
        mapper.addConverter(typeUserToString);
        return mapper.map(model, TypeUserDto.class);
    }

    public List<TypeUserDto> toResourceList(List<TypeUser> modelList) {
        mapper.addConverter(typeUserToString);
        return mapper.mapList(modelList, TypeUserDto.class);
    }

    public TypeUser toModel(TypeUserDto resource){
        return mapper.map(resource,TypeUser.class);
    }

    public Page<TypeUserDto> modelListToPage(List<TypeUser> modelList, Pageable pageable) {

        mapper.addConverter(typeUserToString);
        return new PageImpl<>(mapper.mapList(modelList, TypeUserDto.class), pageable, modelList.size());
    }
}