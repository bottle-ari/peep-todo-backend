package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RoutineResponseDto {
    /**
     *     private String name;
     *     private Boolean is_active;
     *     private Integer priority;
     *     private String repeat_condition;
     *     private String sub_todo;
     *     private Integer orders;
     */
    private Long id;
    private String name;
    private Boolean is_active;
    private Integer priority;
    private String repeat_condition;
    private List<String> sub_todo;
    private Integer orders;

    @Builder
    public RoutineResponseDto(Long id, String name, Boolean is_active, Integer priority, String repeat_condition, List<String> sub_todo, Integer orders) {
        this.id = id;
        this.name = name;
        this.is_active = is_active;
        this.priority = priority;
        this.repeat_condition = repeat_condition;
        this.sub_todo = sub_todo;
        this.orders = orders;
    }
}
