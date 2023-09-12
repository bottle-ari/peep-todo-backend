package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Reminder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TodoRequestDto {
    private String name;
    private LocalDateTime completed_at;
    private String sub_todo;
    private String dates;
    private Integer priority;
    private String memo;
    private Integer orders;
    private Long category_id;
    private Long reminder_id;

    @Builder
    public TodoRequestDto(String name, LocalDateTime completed_at, String sub_todo, String dates, Integer priority, String memo, Integer orders, Long category_id, Long reminder_id) {
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
