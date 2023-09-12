package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ScheduledTodoResponseDto {
    private List<TodoResponseDto> content;

    @Builder
    public ScheduledTodoResponseDto(List<TodoResponseDto> content) {
        this.content = content;
    }
}
