package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
    Optional<List<Category>> findByUserId(Long userId);
}
