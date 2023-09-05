package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.service.GoogleOAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @Autowired
    private GoogleOAuthUserService googleOAuthUserService;  // 사용자와 JWT 처리 서비스

    @GetMapping("/api/oauth2/callback/google")
    public ResponseEntity<?> successGoogleLogin(@RequestParam String code) {
        return ResponseEntity.ok().body(null);
    }
}

