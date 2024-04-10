package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.ActorDto;
import com.upc.TuCine.TuCine.dto.save.Actor.ActorSaveDto;
import com.upc.TuCine.TuCine.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Actor", description = "API de Actores")
@RequestMapping ("/api/TuCine/v1")
public class ActorController {

    @Autowired
    private ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/actors
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/actors")
    @Operation(summary = "Obtener lista de todos los actores")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de actores",
            content = {
                    @Content(mediaType = "application/json",
                    schema= @Schema(implementation = ActorDto.class,type = "array"))
            })
    })
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return new ResponseEntity<List<ActorDto>>(actorService.getAllActors(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/actors
    //Method: POST
    @Transactional
    @PostMapping("/actors")
    @Operation(summary = "Crear un actor")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Se creó el actor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = ActorDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "El actor no se pudo crear",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    public ResponseEntity<ActorDto> createActor(@RequestBody ActorSaveDto actorSaveDto){
        return new ResponseEntity<ActorDto>(actorService.createActor(actorSaveDto), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/actors/{id}")
    @Operation(summary = "Actualizar un actor")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se actualizó el actor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = ActorDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "El actor no se pudo actualizar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    public ResponseEntity<ActorDto> updateActor(@PathVariable Integer id, @RequestBody ActorSaveDto actorSaveDto){
        return new ResponseEntity<>(actorService.updateActor(id, actorSaveDto), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/actors/{id}")
    @Operation(summary = "Eliminar un actor")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se eliminó el actor",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = ActorDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "El actor no se pudo eliminar",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    public ResponseEntity<String> deleteActor(@PathVariable Integer id){
        return new ResponseEntity<>(actorService.deleteActor(id), HttpStatus.OK);
    }

}
