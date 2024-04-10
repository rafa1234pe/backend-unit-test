package com.upc.TuCine.TuCine.controller;


import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.CategoryDto;
import com.upc.TuCine.TuCine.dto.save.Category.CategorySaveDto;
import com.upc.TuCine.TuCine.service.CategoryService;
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
@Tag(name = "Category", description = "API de Categorias")
@RequestMapping("/api/TuCine/v1")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/categories
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/categories")
    @Operation(summary = "Obtener lista de todas las categorias")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de categorias",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = CategoryDto.class,type = "array"))
                    }),
    })
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<List<CategoryDto>>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/categories
    //Method: POST
    @Transactional
    @PostMapping("/categories")
    @Operation(summary = "Crear una categoria de película")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Se creó la categoria",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = CategoryDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "La categoria no se pudo crear",
                    content = @Content)
    })
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategorySaveDto categorySaveDto){
        return new ResponseEntity<>(categoryService.createCategory(categorySaveDto), HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/categories/{id}")
    @Operation(summary = "Actualizar una categoria de película")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se actualizó la categoria",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = CategoryDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "La categoria no se pudo actualizar",
                    content = @Content)
    })
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Integer id, @RequestBody CategorySaveDto categorySaveDto){
        return new ResponseEntity<>(categoryService.updateCategory(id, categorySaveDto), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/categories/{id}")
    @Operation(summary = "Eliminar una categoria de película")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Se eliminó la categoria",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema= @Schema(implementation = String.class))
                    }),
            @ApiResponse(responseCode = "404", description = "La categoria no se pudo eliminar",
                    content = @Content)
    })
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
        return new ResponseEntity<>(categoryService.deleteCategory(id), HttpStatus.OK);
    }
}
