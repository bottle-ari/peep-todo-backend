package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    Optional<Routine> findById(Long id);
    Optional<Routine> findFirstByCategoryIdOrderByOrdersDesc(Long categoryId);
    Optional<List<Routine>> findByCategoryIdOrderByOrdersAsc(Long categoryId);

}
