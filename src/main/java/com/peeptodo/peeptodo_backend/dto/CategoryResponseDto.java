package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String color;
    private String emoji;
    private Integer orders;

    @Builder
    public CategoryResponseDto(Long id, String name, String color, String emoji, Integer orders) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.emoji = emoji;
        this.orders = orders;
    }
}
