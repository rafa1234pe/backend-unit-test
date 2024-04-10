package com.upc.TuCine.TuCine.service.impl;

import com.upc.TuCine.TuCine.dto.ActorDto;

import com.upc.TuCine.TuCine.dto.save.Actor.ActorSaveDto;

import com.upc.TuCine.TuCine.dto.save.Actor.ActorSaveDto;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;

import com.upc.TuCine.TuCine.model.Actor;
import com.upc.TuCine.TuCine.repository.ActorRepository;
import com.upc.TuCine.TuCine.service.ActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ActorServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    private ActorDto EntityToDto(Actor actor){
        return modelMapper.map(actor, ActorDto.class);
    }

    private Actor DtoToEntity(ActorDto actorDto){
        return modelMapper.map(actorDto, Actor.class);
    }

    @Override
    public ActorDto createActor(ActorSaveDto actorSaveDto) {
        ActorDto actorDto = modelMapper.map(actorSaveDto, ActorDto.class);

        validateActor(actorDto);
        existActorByFirstName(actorDto.getFirstName(),actorDto.getLastName());
        Actor actor = DtoToEntity(actorDto);
        return EntityToDto(actorRepository.save(actor));
    }

    @Override
    public List<ActorDto> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        List<ActorDto> actorDtos = new ArrayList<>();
        for (Actor actor : actors) {
            actorDtos.add(EntityToDto(actor));
        }
        return actorDtos;
    }

    @Override
    public ActorDto updateActor(Integer id, ActorSaveDto actorSaveDto) {
        ActorDto actorDto = modelMapper.map(actorSaveDto, ActorDto.class);
        Actor actor = DtoToEntity(actorDto);
        Actor actorUpdate = actorRepository.findById(id).orElseThrow(() -> new ValidationException("No existe el actor"));
        actorUpdate.setFirstName(actor.getFirstName());
        actorUpdate.setLastName(actor.getLastName());
        actorUpdate.setBirthday(actor.getBirthday());
        actorUpdate.setBiography(actor.getBiography());
        actorUpdate.setProfileSrc(actor.getProfileSrc());
        return EntityToDto(actorRepository.save(actorUpdate));
    }

    @Override
    public String deleteActor(Integer id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ValidationException("No existe el actor"));
        actorRepository.delete(actor);
        return "El actor con nombre " + actor.getFirstName() + " " + actor.getLastName() + " ha sido eliminado";
    }


    private void validateActor(ActorDto actor) {
        if (actor.getFirstName() == null || actor.getFirstName().isEmpty()) {
            throw new ValidationException("El nombre es obligatorio");
        }
        if (actor.getLastName() == null || actor.getLastName().isEmpty()) {
            throw new ValidationException("El apellido es obligatorio");
        }
        if (actor.getBirthday() == null) {
            throw new ValidationException("La fecha de nacimiento es obligatoria");
        }
    }

    private void existActorByFirstName(String firstName,String lastName){
        if (actorRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            throw new ValidationException("Ya existe un actor con el nombre " + firstName + " " + lastName);
        }
    }


}
