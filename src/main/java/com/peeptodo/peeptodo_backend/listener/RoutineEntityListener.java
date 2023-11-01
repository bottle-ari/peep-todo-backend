package com.peeptodo.peeptodo_backend.listener;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Routine;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.RoutineRepository;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class RoutineEntityListener extends AbstractEntityListener {

    @Lazy
    @Autowired
    private RoutineRepository routineRepository;

    @Lazy
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @PrePersist
    @PreUpdate
    @PostLoad
    @PreRemove
    public void checkSelf(Object object) {
        assert object instanceof Routine : "object is not Routine instance";
        Routine routine = (Routine) object;
        // 자기 자신인지 검사 (ex : 3번 user가 4번 user의 투두를 수정하는 경우)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;

//        categoryRepository.findById(categoryId)

        Category category = routine.getCategory();
        User routineUser = category.getUser();
        assert routineUser != null : "routineUser is null";
        if (routineUser instanceof HibernateProxy) {
            routineUser = (User)Hibernate.unproxy(routineUser);
        } else if (routineUser instanceof User) {

        } else {
            throw new RuntimeException("category is not HibernateProxy or Category instance");
        }
        assert routineUser != null : "routineUser is null";

        if (!userInPrincipal.equals(routineUser)) {
            throw new SecurityException("자신의 투두만 수정할 수 있습니다.");
        }
    }

}
