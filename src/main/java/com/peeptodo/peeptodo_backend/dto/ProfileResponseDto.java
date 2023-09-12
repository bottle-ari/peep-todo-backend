package com.peeptodo.peeptodo_backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileResponseDto {
    private String name;
    private String email;
    private String picture;
}
