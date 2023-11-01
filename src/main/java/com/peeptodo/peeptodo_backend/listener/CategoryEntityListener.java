package com.peeptodo.peeptodo_backend.listener;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.exception.CategoryRemoveException;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class CategoryEntityListener extends AbstractEntityListener {

    @Lazy
    @Autowired
    private CategoryRepository categoryRepository;

    @PreRemove
    public void preRemove(Category category) {
        checkSelf(category);
        long userId = category.getUser().getId();
        long categoryCount = categoryRepository.countByUserId(userId);
        System.out.println(userId+" id에 해당하는 카테고리 수 (삭제 전) : "+categoryCount);
        if (categoryCount < 2) throw new CategoryRemoveException("카테고리 삭제를 위해서는 카테고리는 최소 2개 이상이어야 합니다.");
    }

//    @PrePersist -> 디폴트 카테고리 생성 시점이 authentication을 받지 못한 시점이라 생성이 안돼서 prepersist는 임시로 제거
    @PreUpdate
    @PostLoad
    @Override
    public void checkSelf(Object object) {
        assert object instanceof Category : "object is not Category instance";
        Category category = (Category) object;
        User categoryUser = category.getUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;
        if (!categoryUser.equals(userInPrincipal)){
            throw new SecurityException("자신의 카테고리만 수정할 수 있습니다.");
        }
    }
}
