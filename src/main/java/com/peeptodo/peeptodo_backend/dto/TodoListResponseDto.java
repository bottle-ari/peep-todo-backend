package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TodoListResponseDto {
    private CategoryResponseDto category;
    private List<TodoRequestDto> todoList;

    @Builder
    public TodoListResponseDto(CategoryResponseDto category, List<TodoRequestDto> todoList) {
        this.category = category;
        this.todoList = todoList;
    }
}
