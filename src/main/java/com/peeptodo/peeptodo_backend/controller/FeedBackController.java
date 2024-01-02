package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.FeedBack;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.dto.FeedBackRequestDto;
import com.peeptodo.peeptodo_backend.dto.FeedBackResponseDto;
import com.peeptodo.peeptodo_backend.repository.FeedBackRepository;
import com.peeptodo.peeptodo_backend.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private FeedBackRepository feedBackRepository;

    @PostMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<FeedBackResponseDto> createFeedBack(@RequestBody FeedBackRequestDto requestDto) {
        FeedBack newFeedBack = feedBackService.createFeedBack(requestDto);
        return ResponseEntity.ok().build();
    }



}
