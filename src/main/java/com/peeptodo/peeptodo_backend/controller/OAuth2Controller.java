package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.config.DomainUrl;
import com.peeptodo.peeptodo_backend.service.GoogleOAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
public class OAuth2Controller {

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    @GetMapping("/api/oauth2/callback/google")
    public ResponseEntity<?> successGoogleLogin(@RequestParam("code") String accessCode, HttpServletResponse response) {
        ResponseEntity<?> googleAccessToken = googleOAuth2Service.getGoogleAccessToken(accessCode, response);
        return googleAccessToken;
    }


    // 23.10.17
    @GetMapping("/api/login/google")
    public ResponseEntity<?> getGoogleAuthUrl(HttpServletRequest request) throws Exception {
        var googleClientId = System.getenv("OAUTH_GOOGLE_ID");
        var googleRedirectUrl = DomainUrl.BACKEND.getValue() + "/api/oauth2/callback/google";

        assert googleClientId != null; // 환경변수 설정 체크

        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUrl
                + "&response_type=code&scope=openid%20profile%20email";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(reqUrl));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);


    }
}

