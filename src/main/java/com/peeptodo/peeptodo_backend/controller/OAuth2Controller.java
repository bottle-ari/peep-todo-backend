package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.service.GoogleOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    @GetMapping("/api/oauth2/callback/google")
    public ResponseEntity<?> successGoogleLogin(@RequestParam String code) {
        return googleOAuth2Service.getGoogleAccessToken(code);
    }
}

