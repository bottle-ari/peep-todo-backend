package com.peeptodo.peeptodo_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO: 10/26/2023 문제가 status가 200
    @ExceptionHandler(CategoryRemoveException.class)
    public ResponseEntity<String> handleCategoryRemoveException(final CategoryRemoveException e) {
        String errorMessage = e.getMessage().isEmpty() ? "카테고리를 삭제하기 위해서는 카테고리가 2개 이상 존재해야합니다." : e.getMessage();
        // TODO: 10/26/2023 아래 status 번호 결정하고 노션에 업로드
        return ResponseEntity.status(500).body(errorMessage);
    }
}
