package com.peeptodo.peeptodo_backend.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String provider_type;

    @Column
    private LocalDateTime created_at;

    @Builder
    public User(String name, String email, String picture, Role role, String provider, String provider_type, LocalDateTime created_at) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.provider_type = provider_type;
        this.created_at = created_at;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
