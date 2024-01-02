package com.peeptodo.peeptodo_backend.config;

//import com.peeptodo.peeptodo_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http.csrf(CsrfConfigurer::disable)
//        .csrf(AbstractHttpConfigurer::disable)
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/resources/**", "/signup", "/about").permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api/oauth2/callback/**")).permitAll() -> 이걸로 하면 작동을 안함..
                        .anyRequest().authenticated())
                            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .exceptionHandling(handler->handler.authenticationEntryPoint(authenticationEntryPoint))
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(request -> request.anyRequest().permitAll());
//
////        http
////            .authorizeHttpRequests(request -> // "/api/oauth2/callback/**"
////                    request.requestMatchers(new AntPathRequestMatcher("/api/oauth2/callback/**")).permitAll()
////                            .anyRequest().authenticated())
////                .exceptionHandling(handler->handler.authenticationEntryPoint(authenticationEntryPoint));;
//        return http.build();
////                .addFilterBefore(new AnonymousCustomFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//        // code20231003162904
//        // 아래가 원본
////        http
////                .csrf(AbstractHttpConfigurer::disable)
////                .cors(cors->cors.configurationSource(corsConfigurationSource()))
////                .formLogin(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(request ->
////                        request.requestMatchers(new AntPathRequestMatcher("/api/oauth2/callback/**")).permitAll()
////                                .anyRequest().authenticated())
////                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
////                .exceptionHandling(handler->handler.authenticationEntryPoint(authenticationEntryPoint));
////        return http.build();
//
//    }


//    @Bean
//    public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
//        // Create a custom anonymous authentication filter
//        return new AnonymousAuthenticationFilter("anonymousUser");
//    }
//
//    @Bean
//    public AnonymousAuthenticationProvider anonymousAuthenticationProvider() {
//        // Create a custom anonymous authentication provider
//        return new AnonymousAuthenticationProvider("anonymousUser");
//    }
//
//    @Bean
//    public AnonymousAuthenticationToken anonymousAuthenticationToken() {
//        // Create a custom anonymous authentication token
//        return new AnonymousAuthenticationToken("anonymousUser", "anonymousUser", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))); // code20231003171837
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
//            throws Exception {
//        return config.getAuthenticationManager();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(DomainUrl.BACKEND.getValue()));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setExposedHeaders(Arrays.asList("Authorization", "RefreshToken"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
