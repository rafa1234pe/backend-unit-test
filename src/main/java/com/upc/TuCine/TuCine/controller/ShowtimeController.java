package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.BusinessTypeDto;
import com.upc.TuCine.TuCine.dto.PromotionDto;
import com.upc.TuCine.TuCine.dto.ShowtimeDto;
import com.upc.TuCine.TuCine.dto.save.Showtime.ShowtimeSaveDto;
import com.upc.TuCine.TuCine.service.ShowtimeService;
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
@Tag(name = "Showtime", description = "API de Showtimes")
@RequestMapping("/api/TuCine/v1")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/showtimes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/showtimes")
    @Operation(summary = "Obtener todos los showtimes")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se obtuvo la lista de showtimes",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ShowtimeDto.class,type = "array")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontraron los showtimes",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<List<ShowtimeDto>> getAllShowtimes() {
        return new ResponseEntity<>(showtimeService.getAllShowtimes(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/showtimes/{id}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/showtimes/{id}")
    @Operation(summary = "Obtener un showtime mediante su id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el showtime",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema= @Schema(implementation = ShowtimeDto.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "No se encontró el showtime",
                            content = @Content)
            }
    )
    public ResponseEntity<ShowtimeDto> getShowtimeById(@PathVariable(value = "id") Integer id) {
        ShowtimeDto showtimeDto = showtimeService.getShowtimeById(id);
        if (showtimeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(showtimeDto, HttpStatus.OK);
    }


    //URL: http://localhost:8080/api/TuCine/v1/showtimes
    //Method: POST
    @Transactional
    @PostMapping("/showtimes")
    @Operation(summary = "Crear un nuevo showtime")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se creó el showtime",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ShowtimeDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se pudo crear el showtime",
                            content = @Content
                    )
            }
    )

    public ResponseEntity<ShowtimeDto> createShowtime(@RequestBody ShowtimeDto showtimeDto) {
        ShowtimeDto createdShowtimeDto = showtimeService.createShowtime(showtimeDto);
        return new ResponseEntity<>(createdShowtimeDto, HttpStatus.CREATED);
    }


    //URL: http://localhost:8080/api/TuCine/v1/showtimes/{id}
    //Method: UPDATE
    @Transactional
    @PutMapping("/showtimes/{id}")

    @Operation(summary = "Actualizar un showtime")
    public ResponseEntity<ShowtimeDto> updateShowtime(@PathVariable(value = "id") Integer id, @RequestBody ShowtimeDto showtimeDto ) {
        ShowtimeDto updatedShowtimeDto = showtimeService.updateShowtime(id, showtimeDto);
        if (updatedShowtimeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedShowtimeDto, HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/showtimes/{id}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/showtimes/{id}")
    @Operation(summary = "Borrar un showtime mediante su id")
    public ResponseEntity<Void> deleteShowtime(@PathVariable(value = "id") Integer id) {
        ShowtimeDto deletedShowtimeDto = showtimeService.deleteShowtime(id);
        if (deletedShowtimeDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/TuCine/v1/showtimes/{filmId}/{businessId}
    // Method: GET
    @GetMapping("/showtimes/{filmId}/{businessId}")
    public ResponseEntity<List<ShowtimeDto>> getShowtimesByFilmAndBusiness(
            @PathVariable("filmId") Integer filmId,
            @PathVariable("businessId") Integer businessId
    ) {
        List<ShowtimeDto> showtimes = showtimeService.getShowtimesByFilmAndBusiness(filmId, businessId);
        if (showtimes.isEmpty()) {
            return ResponseEntity.notFound().build(); // Manejar caso en el que no se encuentren showtimes
        }
        return new ResponseEntity<>(showtimes, HttpStatus.OK);
    }

}
