package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class FeedBackRequestDto {

    @DateTimeFormat(pattern=DateUtils.DATE_PATTERN)
    private LocalDateTime dateTime;
    private String contents;

    @Builder
    public FeedBackRequestDto(LocalDateTime dateTime, String contents) {
        this.dateTime = dateTime;
        this.contents = contents;
    }
}
