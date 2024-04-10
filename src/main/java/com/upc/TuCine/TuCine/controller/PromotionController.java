package com.upc.TuCine.TuCine.controller;


import com.upc.TuCine.TuCine.dto.PromotionDto;

import com.upc.TuCine.TuCine.dto.save.Promotion.PromotionSaveDto;
import com.upc.TuCine.TuCine.dto.save.Promotion.PromotionUpdateDto;

import com.upc.TuCine.TuCine.service.PromotionService;
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
@Tag(name = "Promotion", description = "API de Promotions")
@RequestMapping("/api/TuCine/v1")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }
    //URL: http://localhost:8080/api/TuCine/v1/promotions
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/promotions")
    @Operation(summary = "Obtener todas las promociones")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se obtuvo la lista de promociones",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PromotionDto.class,type = "array")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron las promociones",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<List<PromotionDto>> getAllPromotions() {
        return new ResponseEntity<List<PromotionDto>>(promotionService.getAllPromotions(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/promotions
    //Method: POST
    @Transactional
    @PostMapping("/promotions")
    @Operation(summary = "Crear una nueva promoción")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se creó la promoción",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PromotionDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No se pudo crear la promoción",
                    content = @Content
            )
    })
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionSaveDto promotionSaveDto){
        return new ResponseEntity<>(promotionService.createPromotion(promotionSaveDto), HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/promotions/{id}
    //Method: PUT
    @Transactional
    @Operation(summary = "Actualizar una promoción")
    @PutMapping("/promotions/{id}")
    public ResponseEntity<PromotionDto> updatePromotion(@PathVariable Integer id, @RequestBody PromotionUpdateDto promotionUpdateDto) {
        PromotionDto updatedPromotionDto = promotionService.updatePromotion(id, promotionUpdateDto);
        if (updatedPromotionDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPromotionDto, HttpStatus.OK);
    }


    //URL: http://localhost:8080/api/TuCine/v1/promotions/{id}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/promotions/{id}")
    @Operation(summary = "Borrar una promoción mediante su id")
    public ResponseEntity<PromotionDto> deletePromotion(@PathVariable Integer id) {
        PromotionDto deletedPromotionDto = promotionService.deletePromotion(id);
        if (deletedPromotionDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedPromotionDto, HttpStatus.OK);
    }

}
