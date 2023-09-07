package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TodoResponseDto {
    private CategoryResponseDto category;
    private List<TodoRequestDto> todoList;

    @Builder
    public TodoResponseDto(CategoryResponseDto category, List<TodoRequestDto> todoList) {
        this.category = category;
        this.todoList = todoList;
    }
}
