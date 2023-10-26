package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.dto.CategoriesResponseDto;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.exception.CategoryRemoveException;
import com.peeptodo.peeptodo_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto) {
        Category newCategory = categoryService.createCategory(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CategoriesResponseDto> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories();
        CategoriesResponseDto categoriesResponseDto = new CategoriesResponseDto(categoryResponseDtoList);
        return ResponseEntity.ok().body(categoriesResponseDto);
    }


    /**
     * 카테고리 삭제
     * @param categoryId 카테고리 ID
     * @return
     */
    // TODO: 10/26/2023 api 명세에 response 타입 수정 (Void -> String)
    @PostMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
//        try {
//            글로벌 예외핸들;
//            categoryService.deleteCategory(categoryId);
//        } catch (CategoryRemoveException categoryRemoveException) {
//            String message = categoryRemoveException.getMessage();
//            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR); // 객체와 상태코드를 오류로 리턴
//        }
//        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{categoryId}/name", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long categoryId, @RequestBody String newName) {
        categoryService.updateName(categoryId, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{categoryId}/color", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateColor(@PathVariable Long categoryId, @RequestBody String newColor) {
        categoryService.updateColor(categoryId, newColor);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{categoryId}/emoji", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateEmoji(@PathVariable Long categoryId, @RequestBody String newEmoji) {
        categoryService.updateEmoji(categoryId, newEmoji);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{categoryId}/orders", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateOrders(@PathVariable Long categoryId, @RequestBody Integer newOrders) {
        categoryService.updateOrders(categoryId, newOrders);
        return ResponseEntity.ok().build();
    }
}
