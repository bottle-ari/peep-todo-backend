package com.peeptodo.peeptodo_backend.config.auth.dto;

import com.peeptodo.peeptodo_backend.entity.GoogleUser;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(GoogleUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}