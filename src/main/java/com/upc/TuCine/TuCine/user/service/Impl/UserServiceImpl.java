package com.upc.TuCine.TuCine.user.service.Impl;

import com.upc.TuCine.TuCine.shared.exception.ResourceNotFoundException;
import com.upc.TuCine.TuCine.shared.exception.ValidationException;
import com.upc.TuCine.TuCine.shared.mapping.EnhancedModelMapper;
import com.upc.TuCine.TuCine.user.domain.communication.LoginRequest;
import com.upc.TuCine.TuCine.user.domain.communication.RegisterRequest;
import com.upc.TuCine.TuCine.user.domain.communication.UpdateRequest;
import com.upc.TuCine.TuCine.user.domain.enumeration.Genders;
import com.upc.TuCine.TuCine.user.domain.enumeration.TypeUsers;
import com.upc.TuCine.TuCine.user.domain.model.Gender;
import com.upc.TuCine.TuCine.user.domain.model.TypeUser;
import com.upc.TuCine.TuCine.user.domain.model.User;
import com.upc.TuCine.TuCine.user.mapping.UserMapper;
import com.upc.TuCine.TuCine.user.persistence.GenderRepository;
import com.upc.TuCine.TuCine.user.persistence.TypeUserRepository;
import com.upc.TuCine.TuCine.user.persistence.UserRepository;
import com.upc.TuCine.TuCine.user.resource.TypeUserDto;
import com.upc.TuCine.TuCine.user.resource.UserDto;
import com.upc.TuCine.TuCine.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.usertype.DynamicParameterizedType.ENTITY;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private TypeUserRepository typeUserRepository;

    @Autowired
    private EnhancedModelMapper enhancedMapper;

    private UserMapper mapper;

    UserServiceImpl(UserMapper userMapper) {
        this.mapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return mapper.modelListToResource(userRepository.findAll());
    }

    @Override
    public UserDto getById(Integer userId) {
        return mapper.toResource(userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException(ENTITY,userId)));
    }

    @Override
    public TypeUserDto getTypeUserById(Integer id) {
        User person = userRepository.findById(id).orElse(null);
        if (person == null) {
            return null;
        }
        return enhancedMapper.map(person.getTypeUser(), TypeUserDto.class);
    }

    @Override
    public boolean existsByUserEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean existsUserByDni(String Dni) {
        return userRepository.existsUserByDni(Dni);
    }

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Ya existe una persona registrada con ese email");
        }
        if (userRepository.existsByDni(request.getDni())) {
            throw new ValidationException("Ya existe una persona registrada con ese DNI");
        }

        try {
            Set<String> rolesStringSet = request.getTypeUser();
            Set<TypeUser> roles = new HashSet<>();
            Set<String> gendersStringSet = request.getGender();
            Set<Gender> genders = new HashSet<>();

            if (rolesStringSet == null) {
                typeUserRepository.findByName(TypeUsers.BUSINESS)
                        .map(roles::add)
                        .orElseThrow(() -> new RuntimeException("TypeUser not found."));
            } else {
                rolesStringSet.forEach(roleString ->
                        typeUserRepository.findByName(TypeUsers.valueOf(roleString))
                                .map(roles::add)
                                .orElseThrow(() -> new RuntimeException("TypeUser not found.")));
            }

            if (gendersStringSet == null) {
                genderRepository.findByName(Genders.FEMALE)
                        .map(genders::add)
                        .orElseThrow(() -> new RuntimeException("Gender not found."));
            } else {
                gendersStringSet.forEach(genderString ->
                        genderRepository.findByName(Genders.valueOf(genderString))
                                .map(genders::add)
                                .orElseThrow(() -> new RuntimeException("Gender not found.")));
            }

            logger.info("Roles: {}", roles);
            logger.info("Genders: {}", genders);

            User user = new User()
                    .withFirstName(request.getFirstName())
                    .withLastName(request.getLastName())
                    .withBirthdate(request.getBirthdate())
                    .withDni(request.getDni())
                    .withEmail(request.getEmail())
                    .withPassword(request.getPassword())
                    .withPhone(request.getPhone())
                    .withGender(genders.iterator().next())
                    .withTypeUser(roles.iterator().next())
                    .withCreatedAt(LocalDate.now())
                    .withAddress(request.getAddress());

            userRepository.save(user);
            UserDto resource = enhancedMapper.map(user, UserDto.class);
            return ResponseEntity.ok(resource);

        } catch (Exception e) {
            logger.error("Error on register: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            Optional<User> user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
            if (user.isPresent()) {
                UserDto resource = enhancedMapper.map(user.get(), UserDto.class);
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.badRequest().body("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            logger.error("Error on login: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateUser(Integer userId, UpdateRequest request){
        try {
            Optional<User> user = userRepository.findById(userId);
            user.get().setFirstName(request.getFirstName());
            user.get().setLastName(request.getLastName());
            user.get().setPhone(request.getPhone());
            user.get().setPassword(request.getPassword());
            user.get().setEmail(request.getEmail());
            user.get().setBirthdate(request.getBirthdate());
            user.get().setDni(request.getDni());
            user.get().setImageSrc(request.getImageSrc());
            user.get().setBankAccount(request.getBankAccount());
            user.get().setAddress(request.getAddress());
            UserDto resource = enhancedMapper.map(userRepository.save(user.get()), UserDto.class);
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            logger.error("Error on update: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Integer userId){
        try {
            Optional<User> user = userRepository.findById(userId);
            userRepository.delete(user.get());
            return ResponseEntity.ok("Usuario eliminado");
        } catch (Exception e) {
            logger.error("Error on delete: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
