package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TodoRequestDto {
    private String name;
    private LocalDateTime completed_at;
    private List<String> sub_todo;
    private LocalDateTime dates;
    private Integer priority;
    private String memo;
    private Integer orders;
    private Long category_id;
    private Long reminder_id;


    @Builder
    public TodoRequestDto(String name, LocalDateTime completed_at, List<String> sub_todo, LocalDateTime dates, Integer priority, String memo, Integer orders, Long category_id, Long reminder_id) {
        this.name = name;
        this.completed_at = completed_at;
        this.sub_todo = sub_todo;
        this.dates = dates;
        this.priority = priority;
        this.memo = memo;
        this.orders = orders;
        this.category_id = category_id;
        this.reminder_id = reminder_id;
    }
}
