package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleUserRepository extends JpaRepository<GoogleUser,Long> {
    Optional<GoogleUser> findByEmail(String email);

}
