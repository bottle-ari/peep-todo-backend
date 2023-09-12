package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    public final RefreshTokenService refreshTokenService;

    @GetMapping("/api/token/refresh")
    public ResponseEntity<?> accessTokenReIssueAndLogin(HttpServletResponse response) {
        return refreshTokenService.getAccessToken(response);
    }
}
