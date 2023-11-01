package com.peeptodo.peeptodo_backend.listener;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.Todo;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.ReminderRepository;
import com.peeptodo.peeptodo_backend.repository.TodoRepository;
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

public class ReminderEntityListener extends AbstractEntityListener {

    @Lazy
    @Autowired
    private ReminderRepository reminderRepository;


    @Override
    @PrePersist
    @PreUpdate
    @PostLoad
    @PreRemove
    public void checkSelf(Object object) {
        assert object instanceof Reminder : "object is not Reminder instance";
        Reminder reminder = (Reminder) object;
        // 자기 자신인지 검사 (ex : 3번 user가 4번 user의 투두를 수정하는 경우)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        assert principal instanceof User : "Authentication.principal is not User instance";
        User userInPrincipal = (User) principal;
        User reminderUser = reminder.getUser();
        assert reminderUser != null : "reminderUser is null";

        if (reminderUser instanceof HibernateProxy) {
            reminderUser = (User)Hibernate.unproxy(reminderUser);
        } else if (reminderUser instanceof User) {

        } else {
            throw new RuntimeException("reminderUser is not HibernateProxy or reminderUser instance");
        }

        if (!userInPrincipal.equals(reminderUser)) {
            throw new SecurityException("자신의 리마인더만 수정할 수 있습니다.");
        }
    }

}
