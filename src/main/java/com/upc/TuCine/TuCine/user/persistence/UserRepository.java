package com.upc.TuCine.TuCine.user.persistence;

import com.upc.TuCine.TuCine.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByDni(String dni);


    boolean existsUserByDni(String dni);
    boolean existsUserByEmail(String email);

    Optional<User> findByPhone(String phoneNumber);
    Optional<User> findByEmail(String email);
    Boolean existsByPhone(String phone);
    Boolean existsByEmail(String email);
    Boolean existsByDni(String dni);

    //Find by email and password
    Optional<User> findByEmailAndPassword(String email, String password);
}