package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.FeedBack;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.FeedBackRequestDto;
import com.peeptodo.peeptodo_backend.dto.FeedBackResponseDto;
import com.peeptodo.peeptodo_backend.repository.FeedBackRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import com.peeptodo.peeptodo_backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;

    public FeedBack createFeedBack(FeedBackRequestDto requestDto) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();

        FeedBack feedBack = new FeedBack();
        feedBack.setDateTime(requestDto.getDateTime());
        feedBack.setContents(requestDto.getContents());
        return feedBackRepository.save(feedBack);
    }
}
