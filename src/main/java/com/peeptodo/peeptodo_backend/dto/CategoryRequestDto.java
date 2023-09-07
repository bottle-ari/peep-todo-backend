package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CategoryRequestDto {
    private String name;
    private String emoji;
    private String color;
    private Integer orders;
    private User user;

    @Builder
    public CategoryRequestDto(String name, String color, String emoji, Integer orders, User user) {
        this.name = name;
        this.emoji = emoji;
        this.color = color;
        this.orders = orders;
        this.user = user;
    }
}
