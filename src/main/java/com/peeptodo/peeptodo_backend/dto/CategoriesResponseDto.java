package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoriesResponseDto {
    private List<CategoryResponseDto> categories;

    @Builder
    public CategoriesResponseDto(List<CategoryResponseDto> categories) {
        this.categories = categories;
    }
}

