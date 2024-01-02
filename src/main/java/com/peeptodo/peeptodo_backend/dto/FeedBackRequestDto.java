package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FeedBackRequestDto {

    private LocalDateTime dateTime;
    private String contents;

    @Builder
    public FeedBackRequestDto(LocalDateTime dateTime, String contents) {
        this.dateTime = dateTime;
        this.contents = contents;
    }
}
