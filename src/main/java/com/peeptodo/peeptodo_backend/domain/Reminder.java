package com.peeptodo.peeptodo_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String icon;

    @Column
    private String if_condition;

    @Column
    private String notify_condition;

    @Column
    private Integer orders;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Reminder(String name, String icon, String if_condition, String notify_condition, Integer orders, User user) {
        this.name = name;
        this.icon = icon;
        this.if_condition = if_condition;
        this.notify_condition = notify_condition;
        this.orders = orders;
        this.user = user;
    }
}
