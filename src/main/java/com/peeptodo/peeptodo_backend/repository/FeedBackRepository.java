package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    Optional<FeedBack> findById(Long id);
}
