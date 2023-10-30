package com.peeptodo.peeptodo_backend.domain;

import com.peeptodo.peeptodo_backend.listener.CategoryEntityListener;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(CategoryEntityListener.class)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // code20231026212258
    //    @Column(nullable = false, columnDefinition = "default '#000000'") -> 이렇게 하면 ddl 생성이 안됨..??
    @Column(nullable = false)
    // code20231022204022
    @Check(constraints = "color LIKE '^#[A-Fa-f0-9]{6}$'")
    private String color;


    @Column
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY) // , cascade = CascadeType.ALL
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // code20231022193100 order 로직 구현 후 제약사항 추가 | 같은 투두 내에서는 order 중복 X
    @Column
    private Integer orders;



    @Builder
    public Category(String name, String color, String emoji, Integer orders, User user) {
        this.name = name;
        this.color = color;
        this.emoji = emoji;
        this.orders = orders;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return id != null && id.equals(category.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
