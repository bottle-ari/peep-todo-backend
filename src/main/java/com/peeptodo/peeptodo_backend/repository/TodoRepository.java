package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(Long id);

    @Query("SELECT t FROM Todo t WHERE t.category.id = :categoryId AND t.dates = :fromDate")
    Optional<List<Todo>> findByCategoryIdAndFromDate(@Param("categoryId") Long categoryId, @Param("fromDate") String fromDate);
}
