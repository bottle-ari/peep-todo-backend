package com.peeptodo.peeptodo_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean is_active;

    @Column
    private Integer priority;

    @Column
    private String repeat_condition;

    @Column
    private String sub_todo;

    @Column
    private Integer orders;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "reminder_id")
    private Reminder reminder;

    @Builder
    public Routine(String name, Boolean is_active, Integer priority, String repeat_condition, String sub_todo, Integer orders, Category category, Reminder reminder) {
        this.name = name;
        this.is_active = is_active;
        this.priority = priority;
        this.repeat_condition = repeat_condition;
        this.sub_todo = sub_todo;
        this.orders = orders;
        this.category = category;
        this.reminder = reminder;
    }
}
