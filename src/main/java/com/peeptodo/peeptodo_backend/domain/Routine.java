package com.peeptodo.peeptodo_backend.domain;

import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodoConverter;
import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodos;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

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

    @Convert(converter=SubTodoConverter.class)
    private SubTodos sub_todo;

    @Column(nullable = false)
    private Integer orders;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "reminder_id")
    private Reminder reminder;

    public void setSub_todo(List<String> sub_todo_list) {
        this.sub_todo = new SubTodos(sub_todo_list);
    }

    public List<String> getSub_todo() {
        return this.sub_todo.subTodoStrList();
    }

    @Builder
    public Routine(String name, Boolean is_active, Integer priority, String repeat_condition, SubTodos sub_todo, Integer orders, Category category, Reminder reminder) {
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
