package com.upc.TuCine.TuCine.controller;

import com.upc.TuCine.TuCine.dto.AvailableFilmDto;
import com.upc.TuCine.TuCine.service.AvailableFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/TuCine/v1")
public class AvailableFilmController {
    @Autowired
    private AvailableFilmService availableFilmService;

    public AvailableFilmController(AvailableFilmService availableFilmService) {
        this.availableFilmService = availableFilmService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/availableFilms
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/availableFilms")
    public ResponseEntity<List<AvailableFilmDto>> getAllAvailableFilms() {
        return new ResponseEntity<>(availableFilmService.getAllAvailableFilms(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/availableFilms
    //Method: POST
    @Transactional
    @PostMapping("/availableFilms")
    public ResponseEntity<AvailableFilmDto> createTicket(@RequestBody AvailableFilmDto availableFilmDto){
        AvailableFilmDto createdAvailableFilmDto= availableFilmService.createAvailableFilm(availableFilmDto);
        return new ResponseEntity<>(createdAvailableFilmDto, HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/availableFilms/1
    //Method: PUT
    @Transactional
    @PutMapping("/availableFilms/{id}")
    public ResponseEntity<AvailableFilmDto> updateTicket(@PathVariable Integer id, @RequestBody AvailableFilmDto availableFilmDto){
        AvailableFilmDto updatedAvailableFilmDto = availableFilmService.updateAvailableFilm(id, availableFilmDto);
        if (updatedAvailableFilmDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAvailableFilmDto,HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/availableFilms/1
    //Method: DELETE
    @Transactional
    @DeleteMapping("/availableFilms/{id}")
    public ResponseEntity<AvailableFilmDto> deleteTicket(@PathVariable Integer id){
        AvailableFilmDto deletedAvailableFilmDto = availableFilmService.deleteAvailableFilm(id);
        if (deletedAvailableFilmDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedAvailableFilmDto,HttpStatus.OK);
    }
}