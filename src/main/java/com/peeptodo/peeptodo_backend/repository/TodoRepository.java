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



    Optional<Todo> findFirstByCategoryIdOrderByOrdersDesc(Long categoryId);


    @Query("SELECT t FROM Todo t WHERE t.category.id = :categoryId AND " +
            "t.completed_at >= to_timestamp(:fromDate, 'YYYY-MM-DD\"T\"HH24:MI:SS') AND " +
            "t.completed_at <= to_timestamp(:toDate, 'YYYY-MM-DD\"T\"HH24:MI:SS')")
    Optional<List<Todo>> findByCategoryIdAndCompletedAt(@Param("categoryId") Long categoryId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query("SELECT t FROM Todo t WHERE t.category.id = :categoryId AND " +
            "t.dates >= to_timestamp(:fromDate, 'YYYY-MM-DD\"T\"HH24:MI:SS')")
    Optional<List<Todo>> findByCategoryIdAndFromDate(@Param("categoryId") Long categoryId, @Param("fromDate") String fromDate);

    // 지연된 투두 -> dates속성이 지금보다 이전이면서 completed_at이 null인 투두
    @Query("SELECT t FROM Todo t WHERE t.category.id = :categoryId AND " +
            "t.dates <= to_timestamp(:toDate, 'YYYY-MM-DD\"T\"HH24:MI:SS') and completed_at is null")
    Optional<List<Todo>> findByCategoryIdAndToCompletedAt(@Param("categoryId") Long categoryId, @Param("toDate") String toDate);

    @Query("SELECT t FROM Todo t WHERE t.category.id = :categoryId AND " +
            "t.dates >= to_timestamp(:fromDate, 'YYYY-MM-DD\"T\"HH24:MI:SS') AND " +
            "t.dates <= to_timestamp(:toDate, 'YYYY-MM-DD\"T\"HH24:MI:SS')")
    Optional<List<Todo>> findByCategoryIdAndFromDateAndToDate(@Param("categoryId") Long categoryId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
