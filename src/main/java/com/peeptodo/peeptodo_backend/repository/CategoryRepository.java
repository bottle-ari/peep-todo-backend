package com.peeptodo.peeptodo_backend.repository;

import com.peeptodo.peeptodo_backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
    Optional<List<Category>> findByUserId(Long userId);
    Long countByUserId(Long userId); // 유저 아이디에 해당하는 카테고리 수

//    @Query(value = "SELECT orders FROM CATEGORY where user_id", nativeQuery = true)
    // 참고 : @Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
//    @Query("SELECT c.orders FROM ADMIN.CATEGORY c where c.user_id = ?1")
//    @Query("SELECT c.orders FROM ADMIN.CATEGORY c where c.user_id = ?1")
//    Optional<Integer> findMaxOrdersByUserId(Long userId); // 카테고리의 amount 중 가장 큰 값
    Optional<Category> findFirstByUserIdOrderByOrdersDesc(Long userId);
}
