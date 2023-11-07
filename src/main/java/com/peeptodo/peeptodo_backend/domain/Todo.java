package com.peeptodo.peeptodo_backend.domain;

import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodoConverter;
import com.peeptodo.peeptodo_backend.domain.subtodo.SubTodos;
import com.peeptodo.peeptodo_backend.listener.TodoEntityListener;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(TodoEntityListener.class)
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime completed_at;

    @Convert(converter= SubTodoConverter.class)
    private SubTodos sub_todo;

    @Column
    private LocalDateTime dates;

    @Column
    private Integer priority;

    @Column
    private String memo;

    @Column(nullable = false)
    private Integer orders;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
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
    public Todo(String name, LocalDateTime completed_at, List<String> sub_todo, LocalDateTime dates, Integer priority, String memo, Integer orders, Category category, Reminder reminder) {
        this.name = name;
        this.completed_at = completed_at;
        this.sub_todo = new SubTodos(sub_todo);
        this.dates = dates;
        this.priority = priority;
        this.memo = memo;
        this.orders = orders;
        this.category = category;
        this.reminder = reminder;
    }
}
