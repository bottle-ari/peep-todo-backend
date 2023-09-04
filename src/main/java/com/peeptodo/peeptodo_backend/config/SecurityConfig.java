package com.peeptodo.peeptodo_backend.config;

import com.peeptodo.peeptodo_backend.domain.Role;
import com.peeptodo.peeptodo_backend.service.GoogleOAuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //시큐리티 활성화 -> 기본 스프링 필터 체인에 등록
public class SecurityConfig {

    @Autowired
    private GoogleOAuthUserService googleOauthUserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/api/**")).hasRole(Role.USER.name())
                                .anyRequest().authenticated())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(login ->
                        login
                                .userInfoEndpoint(userInfo -> userInfo.userService(googleOauthUserService))
                );


        return http.build();
    }
}
