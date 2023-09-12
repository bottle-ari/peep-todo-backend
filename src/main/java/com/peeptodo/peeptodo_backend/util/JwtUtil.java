package com.peeptodo.peeptodo_backend.util;

import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserService userService;

    private final byte[] SECRET_KEY = Decoders.BASE64.decode(System.getenv("JWT_SECRET"));

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername(), 1000 * 60 * 60 * 48); // 48시간
    }

    public String generateRefreshToken(UserDetails userDetails) {
        long duration = 1000 * 60 * 60 * 24 * 7; // 7일
        String token = createToken(userDetails.getUsername(), duration);

        userService.saveRefreshToken(userDetails.getUsername(), token, duration);

        return token;
    }

    public String createToken(String email, long duration) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return e.getClaims();
        } catch (io.jsonwebtoken.JwtException e) {
            throw new SecurityException("Invalid JWT token : Extract failed", e);
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractAllClaims(token).getSubject();
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Transactional
    public boolean validateRefreshToken(String email, String refreshToken) {
        User user = userService.getProfileByEmail(email);
        if (user == null) return false;
        return user.getRefreshToken().getToken().equals(refreshToken) &&
                !user.getRefreshToken().getExpiryTime().before(new Date());
    }

    public Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

}
