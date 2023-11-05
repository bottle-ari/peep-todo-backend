package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RemindersResponseDto {
    private List<ReminderResponseDto> reminders;

    @Builder
    public RemindersResponseDto(List<ReminderResponseDto> reminders) {
        this.reminders = reminders;
    }
}

