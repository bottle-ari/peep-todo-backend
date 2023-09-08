package com.peeptodo.peeptodo_backend.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.peeptodo.peeptodo_backend.config.TokenProvider;
import com.peeptodo.peeptodo_backend.dto.GoogleOAuthResponseDto;
import com.peeptodo.peeptodo_backend.dto.ProfileResponseDto;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleOAuth2Service {
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private String GOOGLE_CLIENT_ID = System.getenv("OAUTH_GOOGLE_ID");
    private String GOOGLE_CLIENT_SECRET = System.getenv("OAUTH_GOOGLE_SECRET");
    private String LOGIN_REDIRECT_URL = "http://localhost:8080/api/oauth2/callback/google";

    @Autowired
    private UserService userService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public ResponseEntity<String> getGoogleAccessToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", LOGIN_REDIRECT_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<GoogleOAuthResponseDto> responseEntity
                = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, GoogleOAuthResponseDto.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            String idToken = Objects.requireNonNull(responseEntity.getBody()).getId_token();

            String[] parts = idToken.split("\\.");
            String payload = parts[1];
            String decodedPayload = new String(Base64.getDecoder().decode(payload));

            JsonObject jsonObject = JsonParser.parseString(decodedPayload).getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String email = jsonObject.get("email").getAsString();
            String picture = jsonObject.get("picture").getAsString();

            if(userService.getProfileByEmail(email) == null) {
                userService.createProfileWithGoogle(name, email, picture);
            }

            // ToDo : 여기서 인증 토큰 발급하기 & JWT 토큰 사용하기

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());


            // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // 해당 객체를 SecurityContextHolder에 저장하고
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
            String jwt = tokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            // response header에 jwt token에 넣어줌
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            // tokenDto를 이용해 response body에도 넣어서 리턴
            return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
        }
        return null;
    }
}
