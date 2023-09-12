package com.peeptodo.peeptodo_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.peeptodo.peeptodo_backend.dto.ProfileResponseDto;
import com.peeptodo.peeptodo_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/profiles")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ProfileResponseDto> getProfile() throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        ProfileResponseDto profileResponseDto = userService.getProfile();
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PatchMapping(value = "/name/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long id, @RequestBody String newName) {
        userService.updateName(id, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/image/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateImage(@PathVariable Long id, @RequestBody String newImage) {
        userService.updateImage(id, newImage);
        return ResponseEntity.ok().build();
    }

}
