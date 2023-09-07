package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.ProfileResponseDto;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
