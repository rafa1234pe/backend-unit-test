package com.upc.TuCine.TuCine.user.service;

import com.upc.TuCine.TuCine.user.domain.communication.LoginRequest;
import com.upc.TuCine.TuCine.user.domain.communication.RegisterRequest;
import com.upc.TuCine.TuCine.user.domain.communication.UpdateRequest;
import com.upc.TuCine.TuCine.user.resource.TypeUserDto;
import com.upc.TuCine.TuCine.user.resource.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getById(Integer userId);

    TypeUserDto getTypeUserById(Integer id);

    //UserDto registerUser(UserDto userDto);

    boolean existsByUserEmail (String email);

    boolean existsUserByDni(String Dni);

    ResponseEntity<?> register(RegisterRequest request);

    //Login
    ResponseEntity<?> login(LoginRequest request);

    //Update user
    ResponseEntity<?> updateUser(Integer userId, UpdateRequest request);

    //Delete user
    ResponseEntity<?> deleteUser(Integer userId);
}
