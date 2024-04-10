package com.upc.TuCine.TuCine.user.persistence;

import com.upc.TuCine.TuCine.user.domain.enumeration.TypeUsers;
import com.upc.TuCine.TuCine.user.domain.model.TypeUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeUserRepository extends JpaRepository<TypeUser, Integer> {
    Optional<TypeUser> findByName(TypeUsers name);
    boolean existsByName(TypeUsers name);
}
