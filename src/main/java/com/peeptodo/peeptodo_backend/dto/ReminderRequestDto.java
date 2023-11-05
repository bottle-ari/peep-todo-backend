package com.peeptodo.peeptodo_backend.dto;

import com.peeptodo.peeptodo_backend.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReminderRequestDto {

    private String name;
    private String icon;
    private String if_condition;
    private String notify_condition;
    private Integer orders;
    private User user;



    @Builder
    public ReminderRequestDto(String name, String icon, String if_condition,String notify_condition, Integer orders, User user) {
        this.name = name;
        this.icon = icon;
        this.if_condition = if_condition;
        this.notify_condition = notify_condition;
        this.orders = orders;
        this.user = user;
    }
}
