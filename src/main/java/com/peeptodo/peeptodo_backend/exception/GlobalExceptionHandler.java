package com.peeptodo.peeptodo_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CategoryRemoveException.class)
    public ResponseEntity<String> handleCategoryRemoveException(final CategoryRemoveException e) {
        String errorMessage = e.getMessage().isEmpty() ? "카테고리를 삭제하기 위해서는 카테고리가 2개 이상 존재해야합니다." : e.getMessage();
        // 422 Unprocessable Entity
        return ResponseEntity.status(422).body(errorMessage);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleSecurityException(final SecurityException e) {
        String errorMessage = e.getMessage().isEmpty() ? "잘못된 유저 정보 접근" : e.getMessage();
        // 403 Forbidden
        return ResponseEntity.status(403).body(errorMessage);
    }
}
