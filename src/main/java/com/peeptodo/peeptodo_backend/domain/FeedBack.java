package com.peeptodo.peeptodo_backend.domain;

import com.peeptodo.peeptodo_backend.listener.FeedbackEntityListener;
import com.peeptodo.peeptodo_backend.listener.TodoEntityListener;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(FeedbackEntityListener.class)
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String contents;

    @Column
    private LocalDateTime dateTime;

    @Builder
    public FeedBack(String contents, LocalDateTime dateTime) {
        this.contents = contents;
        this.dateTime = dateTime;
    }
}
