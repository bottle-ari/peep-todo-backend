package com.peeptodo.peeptodo_backend.config;

import com.peeptodo.peeptodo_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (request.getRequestURI().startsWith("/api/login/google")) {
                filterChain.doFilter(request, response);
                return;
            } else if (request.getRequestURI().startsWith("/api/oauth2/callback")) {
                filterChain.doFilter(request, response);
                return;
            }

            Cookie[] cookies = request.getCookies();
            String email = null;
            String jwt = null;
            String refreshToken = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        email =  jwtUtil.extractAllClaims(jwt).getSubject();
                    } else if ("refresh_token".equals(cookie.getName())) {
                        refreshToken = cookie.getValue();
                    }
                }
            }

            if (email != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    setAuthentication(userDetails, request);
                } else if (jwtUtil.validateRefreshToken(email, refreshToken)) {
                    setAuthentication(userDetails, request);
                } else {
                    throw new SecurityException("Invalid JWT token");
                }
            } else {
                throw new SecurityException("Invalid JWT token");
            }
        } catch (SecurityException e) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, new AuthenticationException("Invalid JWT token", e) {
                @Override
                public String toString() {
                    return super.toString();
                }
            });
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
