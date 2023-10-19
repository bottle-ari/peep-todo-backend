package com.peeptodo.peeptodo_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // TODO: 10/19/2023 다크모드, 라이트모드에 따라서 색상이 달라지도록..?
    // TODO: 10/19/2023 헥스 형식으로 제약 걸기

    @Column(nullable = false)
//    @Column(nullable = false, columnDefinition = "default '#000000'") -> 이렇게 하면 ddl 생성이 안됨..??
    private String color;

    // TODO: 10/19/2023 이모지 없는 경우???
    @Column
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY) // , cascade = CascadeType.ALL
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // TODO: 10/19/2023 order 로직 구현 후 제약사항 추가 | 같은 todo 내에서는 order 중복 X
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
}
