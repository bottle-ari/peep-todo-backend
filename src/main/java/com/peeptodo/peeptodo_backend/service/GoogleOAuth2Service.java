package com.peeptodo.peeptodo_backend.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.peeptodo.peeptodo_backend.config.DomainUrl;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.GoogleOAuthResponseDto;
import com.peeptodo.peeptodo_backend.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2Service {
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_CLIENT_ID = System.getenv("OAUTH_GOOGLE_ID");
    private final String GOOGLE_CLIENT_SECRET = System.getenv("OAUTH_GOOGLE_SECRET");
    private final String LOGIN_REDIRECT_URL = DomainUrl.BACKEND.getValue() + "/api/oauth2/callback/google";

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<?> getGoogleAccessToken(String accessCode, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        // Google OAuth2 Token 발급 받기
        ResponseEntity<GoogleOAuthResponseDto> responseEntity
                = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, GoogleOAuthResponseDto.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            // 성공 시 Token Decode & 회원 조회
            String idToken = Objects.requireNonNull(responseEntity.getBody()).getId_token();

            String[] parts = idToken.split("\\.");
            String payload = parts[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));

            JsonObject jsonObject = JsonParser.parseString(decodedPayload).getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String email = jsonObject.get("email").getAsString();
            String picture = jsonObject.get("picture").getAsString();

            User user = userService.getProfileByEmail(email);

            // 회원이 존재하지 않는다면 DB에 회원 생성시키기
            if(user == null) {
                user = userService.createProfileWithGoogle(name, email, picture);
            }

            //인증 토큰 발급하기 & JWT 토큰 사용하기
            String jwtToken = jwtUtil.generateToken(user);
            String jwtRefreshToken = jwtUtil.generateRefreshToken(user);

            Cookie accessTokenCookie = new Cookie("access_token", jwtToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(true); // HTTPS 환경에서만 사용하도록 설정
            accessTokenCookie.setPath("/");

            Cookie refreshTokenCookie = new Cookie("refresh_token", jwtRefreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(true);
            refreshTokenCookie.setPath("/api/token/refresh");

            // 쿠키를 응답에 추가합니다.
            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(DomainUrl.FRONTEND.getValue() + "/scheduled_todo");

            URI uri = uriBuilder.build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uri);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return null;
    }
}