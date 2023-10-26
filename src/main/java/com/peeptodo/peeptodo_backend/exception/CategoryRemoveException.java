package com.peeptodo.peeptodo_backend.exception;

/**
 * 카테고리 한개 이하로 존재하면 카테고리 삭제 불가능
 */
public class CategoryRemoveException extends RuntimeException{
    public CategoryRemoveException(String message) {
        super(message);
    }
}
