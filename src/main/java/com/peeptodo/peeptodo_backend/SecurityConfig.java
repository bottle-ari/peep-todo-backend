//package com.peeptodo.peeptodo_backend;
//
//import com.peeptodo.peeptodo_backend.service.GoogleOAuth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//    private final GoogleOAuth2UserService googleOAuth2UserService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        return http.csrf(csrf->csrf.disable()).
//                authorizeHttpRequests((authorizeRequests) ->
//        authorizeRequests.anyRequest().permitAll()).build();
//    }
//}
