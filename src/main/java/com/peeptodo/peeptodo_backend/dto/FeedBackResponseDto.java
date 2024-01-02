package com.peeptodo.peeptodo_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FeedBackResponseDto {
    private LocalDateTime dateTime;
    private String contents;
}
