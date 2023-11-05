package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Optional<Reminder> findById(Long id);
    Optional<List<Reminder>> findByUserId(Long userId);
    Optional<Reminder> findFirstByUserIdOrderByOrdersDesc(Long userId);
}
