package com.upc.TuCine.TuCine.user.controller;

import com.upc.TuCine.TuCine.user.domain.communication.LoginRequest;
import com.upc.TuCine.TuCine.user.domain.communication.RegisterRequest;
import com.upc.TuCine.TuCine.user.domain.communication.UpdateRequest;
import com.upc.TuCine.TuCine.user.resource.UserDto;
import com.upc.TuCine.TuCine.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/TuCine/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    //URL: http://localhost:8080/api/TuCine/v1/users
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/users/{userId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/users/auth/sign-up
    //Method: POST
    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    //URL: http://localhost:8080/api/TuCine/v1/users/auth/sign-in
    //Method: POST
    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    //Update user
    //URL: http://localhost:8080/api/TuCine/v1/users/{userId}
    //Method: PUT
    @PutMapping("{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody UpdateRequest request ){
        return new ResponseEntity<>(userService.updateUser(userId,request), HttpStatus.OK);
    }

    //Delete user
    //URL: http://localhost:8080/api/TuCine/v1/users/{userId}
    //Method: DELETE
    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}