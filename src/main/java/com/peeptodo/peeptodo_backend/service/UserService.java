package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.RefreshToken;
import com.peeptodo.peeptodo_backend.domain.Role;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.ProfileResponseDto;
import com.peeptodo.peeptodo_backend.repository.RefreshTokenRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public User createProfileWithGoogle(String name, String email, String picture) {
        User user = User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .provider("google")
                .role(Role.USER)
                .created_at(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public void saveRefreshToken(String email, String token, long duration) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(("User not found!")));

        RefreshToken refreshToken;

        if(user.getRefreshToken() == null) {
            refreshToken = RefreshToken.builder()
                    .token(token)
                    .createdTime(new Date(System.currentTimeMillis()))
                    .expiryTime(new Date(System.currentTimeMillis() + duration))
                    .build();
        } else {
            refreshToken = user.getRefreshToken();
            refreshToken.setToken(token);
            refreshToken.setCreatedTime(new Date(System.currentTimeMillis()));
            refreshToken.setExpiryTime(new Date(System.currentTimeMillis() + duration));

        }

        refreshToken = refreshTokenRepository.save(refreshToken);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Transactional
    public void deleteRefreshToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(("User not found!")));
        user.setRefreshToken(null);
        userRepository.save(user);
    }


    public User getProfileByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public ProfileResponseDto getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(("User not found!")));
        return new ProfileResponseDto(user.getName(), user.getEmail(), user.getPicture());
    }

    public void updateName(Long userId, String newName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(newName);
        userRepository.save(user);
    }

    public void updateImage(Long userId, String newImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPicture(newImage);
        userRepository.save(user);
    }

    public void terminate(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
