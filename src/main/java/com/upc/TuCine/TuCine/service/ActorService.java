package com.upc.TuCine.TuCine.service;

import com.upc.TuCine.TuCine.dto.ActorDto;
import com.upc.TuCine.TuCine.dto.save.Actor.ActorSaveDto;

import java.util.List;

public interface ActorService {
    ActorDto createActor(ActorSaveDto actorSaveDto);

    List<ActorDto> getAllActors();

    ActorDto updateActor(Integer id,ActorSaveDto actorSaveDto);

    String deleteActor(Integer id);

}
