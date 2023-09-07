package com.peeptodo.peeptodo_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProfileResponseDto {
    private String name;
    private String email;
    private String picture;

    @Builder
    public ProfileResponseDto(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
