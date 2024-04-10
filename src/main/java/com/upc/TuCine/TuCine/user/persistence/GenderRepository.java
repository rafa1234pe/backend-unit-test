package com.upc.TuCine.TuCine.user.persistence;

import com.upc.TuCine.TuCine.user.domain.enumeration.Genders;
import com.upc.TuCine.TuCine.user.domain.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender,Integer> {

    Optional<Gender> findByName(Genders name);
    boolean existsByName(Genders name);
}