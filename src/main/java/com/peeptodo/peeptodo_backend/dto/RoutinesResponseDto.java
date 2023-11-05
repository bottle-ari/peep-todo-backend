package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RoutinesResponseDto {
    private List<RoutineResponseDto> routines;

    @Builder
    public RoutinesResponseDto(List<RoutineResponseDto> routines) {
        this.routines = routines;
    }
}

