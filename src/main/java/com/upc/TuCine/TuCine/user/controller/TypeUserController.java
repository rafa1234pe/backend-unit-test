package com.upc.TuCine.TuCine.user.controller;

import com.upc.TuCine.TuCine.user.resource.TypeUserDto;
import com.upc.TuCine.TuCine.user.service.TypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/TuCine/v1")
public class TypeUserController {

    @Autowired
    private TypeUserService typeUserService;

    public TypeUserController(TypeUserService typeUserService) {
        this.typeUserService = typeUserService;
    }

    //URL: http://localhost:8080/api/TuCine/v1/typeUsers
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/typeUsers")
    //@Operation(summary = "Obtener todos los TypeUsers", description = "Obtiene la lista de todos los tipos de usuarios disponibles")
    public ResponseEntity<List<TypeUserDto>> getAllTypeUsers() {
        return new ResponseEntity<List<TypeUserDto>>(typeUserService.getAllTypeUsers(), HttpStatus.OK);
    }
}
