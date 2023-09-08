package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Role;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.ProfileResponseDto;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createProfileWithGoogle(String name, String email, String picture) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPicture(picture);
        user.setProvider("google");
        user.setRole(Role.USER);
        user.setCreated_at(LocalDateTime.now());
        return userRepository.save(user);
    }

    public ProfileResponseDto getProfileByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new ProfileResponseDto(user.getName(), user.getEmail(), user.getPicture()))
                .orElse(null);
    }

    public ProfileResponseDto getProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(("User not found!")));
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
}
