package com.upc.TuCine.TuCine.controller;


import com.upc.TuCine.TuCine.dto.CategoryDto;
import com.upc.TuCine.TuCine.dto.ContentRatingDto;
import com.upc.TuCine.TuCine.dto.save.ContentRating.ContentRatingSaveDto;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;

import com.upc.TuCine.TuCine.dto.save.ContentRating.ContentRatingSaveDto;

import com.upc.TuCine.TuCine.service.ContentRatingService;
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
@Tag(name = "ContentRating", description = "API de ContentRating")
@RequestMapping("/api/TuCine/v1")

public class ContentRatingController {

    @Autowired
    private ContentRatingService contentRatingService;

    public ContentRatingController(ContentRatingService contentRatingService) {
        this.contentRatingService = contentRatingService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/contentRatings")
    @Operation(summary = "Obtener todos los clasificaciones de contenido que existen para las películas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se obtuvo la lista de clasificaciones de contenido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContentRatingDto.class, type = "array")
                            )
                    }
            ),

    })
    public ResponseEntity<List<ContentRatingDto>> getAllContentRatings() {
        return new ResponseEntity<>(contentRatingService.getAllContentRatings(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings/{id}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/contentRatings/{id}")
    @Operation(summary = "Obtener una clasificación de contenido por su id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se obtuvo la clasificación de contenido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContentRatingDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la clasificación de contenido",
                    content = @Content
            )
    })
    public ResponseEntity<ContentRatingDto> getContentRatingById(@PathVariable("id") Integer id) {
        return new ResponseEntity<ContentRatingDto>(contentRatingService.getContentRatingById(id), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings
    //Method: POST
    @Transactional
    @PostMapping("/contentRatings")
    @Operation(summary = "Crear una clasificación de contenido")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se creó la clasificación de contenido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContentRatingDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No se pudo crear la clasificación de contenido",
                    content = @Content
            )
    })
    public ResponseEntity<ContentRatingDto> createContentRating(@RequestBody ContentRatingSaveDto contentRatingSaveDto){
        return new ResponseEntity<ContentRatingDto>(contentRatingService.createContentRating(contentRatingSaveDto), HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/contentRatings/{id}
    //Method: PUT
    @Transactional
    @PutMapping("/contentRatings/{id}")
    @Operation(summary = "Actualizar una clasificación de contenido por su id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se actualizó la clasificación de contenido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContentRatingDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la clasificación de contenido",
                    content = @Content
            )
    })
    public ResponseEntity<ContentRatingDto> updateContentRating(@PathVariable("id") Integer id, @RequestBody ContentRatingSaveDto contentRatingSaveDto){
        return new ResponseEntity<ContentRatingDto>(contentRatingService.updateContentRating(id, contentRatingSaveDto), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/contentRatings/{id}")
    @Operation(summary = "Eliminar una clasificación de contenido por su id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se eliminó la clasificación de contenido",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContentRatingDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la clasificación de contenido",
                    content = @Content
            )
    })
    public ResponseEntity<String> deleteContentRating(@PathVariable Integer id){
        return new ResponseEntity<>(contentRatingService.deleteContentRating(id), HttpStatus.OK);
    }
}
