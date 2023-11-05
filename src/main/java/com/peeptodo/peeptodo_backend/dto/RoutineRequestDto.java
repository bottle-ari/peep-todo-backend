package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoutineRequestDto {

    private String name;
    private Boolean is_active;
    private Integer priority;
    private String repeat_condition;
    private String sub_todo;
    private Integer orders;
    private Category category;
    private Reminder reminder;

    @Builder
    public RoutineRequestDto(String name, Boolean is_active, Integer priority, String repeat_condition, String sub_todo, Integer orders, Category category, Reminder reminder) {
        this.name = name;
        this.is_active = is_active;
        this.priority = priority;
        this.repeat_condition = repeat_condition;
        this.sub_todo = sub_todo;
        this.orders = orders;
        this.category = category;
        this.reminder = reminder;
    }
}
