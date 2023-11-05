package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReminderResponseDto {
    private Long id;
    private String name;
    private String icon;
    private String if_condition;
    private String notify_condition;
    private Integer orders;

    @Builder
    public ReminderResponseDto(Long id, String name, String icon, String if_condition, String notify_condition, Integer orders) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.if_condition = if_condition;
        this.notify_condition = notify_condition;
        this.orders = orders;
    }
}
