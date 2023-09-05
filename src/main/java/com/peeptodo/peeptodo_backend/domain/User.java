package com.peeptodo.peeptodo_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    private String provider;

    @Column
    private String providerId;

    @Column
    private LocalDateTime created_at;

    @Builder
    public User(String name, String email, String picture, Role role, String provider, String providerId, LocalDateTime created_at) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.created_at = created_at;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
