package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ScheduledTodoResponseDto {
    private List<TodoListResponseDto> content;

    @Builder
    public ScheduledTodoResponseDto(List<TodoListResponseDto> content) {
        this.content = content;
    }
}
