package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import com.peeptodo.peeptodo_backend.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public ResponseEntity<?> getAccessToken(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(("User not found!")));

        String jwtToken = jwtUtil.generateToken(user);

        Cookie accessTokenCookie = new Cookie("access_token", jwtToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS 환경에서만 사용하도록 설정
        accessTokenCookie.setPath("/"); // 쿠키 유효 경로 설정

        response.addCookie(accessTokenCookie);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
