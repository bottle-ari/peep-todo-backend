package com.peeptodo.peeptodo_backend.listener;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.exception.CategoryRemoveException;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class CategoryEntityListener {

    @Lazy
    @Autowired
    private CategoryRepository categoryRepository;

    @PreRemove
    public void preRemove(Category category) {
        long userId = category.getUser().getId();
        long categoryCount = categoryRepository.countByUserId(userId);
        System.out.println(userId+" id에 해당하는 카테고리 수 (삭제 전) : "+categoryCount);
        if (categoryCount < 2) throw new CategoryRemoveException("카테고리 삭제를 위해서는 카테고리는 최소 2개 이상이어야 합니다.");
        // TODO: 10/26/2023 카테고리 개수 1개일 때는 삭제 불가능하게 만들기
    }
}
