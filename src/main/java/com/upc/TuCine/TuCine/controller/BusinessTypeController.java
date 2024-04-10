package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.save.BusinessType.BusinessTypeDtoSave;
import com.upc.TuCine.TuCine.service.BusinessTypeService;
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
@Tag(name = "BusinessType", description = "API de Tipos de Negocio")
@RequestMapping("/api/TuCine/v1")
public class BusinessTypeController {

    @Autowired
    private BusinessTypeService businessTypeService;

    public BusinessTypeController(BusinessTypeService businessTypeService) {
        this.businessTypeService = businessTypeService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/businessTypes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businessTypes")
    @Operation(summary = "Obtener lista de todos los tipos de negocio")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de todos los tipos de negocio",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessTypeDto.class, type = "array"))
                    }),
    })
    public ResponseEntity<List<BusinessTypeDto>> getAllBusinessTypes() {
        return new ResponseEntity<>(businessTypeService.getAllBusinessTypes(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/businessTypes/{id}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/businessTypes/{id}")
    @Operation(summary = "Obtener un tipo de negocio por su id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo el tipo de negocio",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessTypeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "No se encontró el tipo de negocio",
                    content = @Content)
    })
    public ResponseEntity<BusinessTypeDto> getBusinessTypeById(@PathVariable(value = "id") Integer id) {

        BusinessTypeDto businessTypeDto = businessTypeService.getBusinessTypeById(id);
        if (businessTypeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(businessTypeDto, HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/businessTypes
    //Method: POST
    @Transactional
    @PostMapping("/businessTypes")
    @Operation(summary = "Crear un tipo de negocio")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Se creó el tipo de negocio",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = BusinessTypeDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "No se pudo crear el tipo de negocio",
                    content = @Content)
    })
    public ResponseEntity<BusinessTypeDto> createBusinessType(@RequestBody BusinessTypeDtoSave businessTypeDtoSave){
        return new ResponseEntity<>(businessTypeService.createBusinessType(businessTypeDtoSave), HttpStatus.CREATED);
    }


}
