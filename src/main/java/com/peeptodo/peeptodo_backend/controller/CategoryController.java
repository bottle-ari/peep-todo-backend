package com.peeptodo.peeptodo_backend.controller;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.dto.CategoriesResponseDto;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
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

    @PostMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CategoryResponseDto> createCategory(@PathVariable Long id, @RequestBody CategoryRequestDto requestDto) {
        Category newCategory = categoryService.createCategory(requestDto, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CategoriesResponseDto> getAllCategories(@PathVariable Long id) {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories(id);
        CategoriesResponseDto categoriesResponseDto = new CategoriesResponseDto(categoryResponseDtoList);
        return ResponseEntity.ok().body(categoriesResponseDto);
    }

    @PostMapping("/{categoryId}/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId, Long id) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/name/{categoryId}/name", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateName(@PathVariable Long categoryId, @RequestBody String newName) {
        categoryService.updateName(categoryId, newName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/name/{categoryId}/color", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateColor(@PathVariable Long categoryId, @RequestBody String newColor) {
        categoryService.updateColor(categoryId, newColor);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/name/{categoryId}/emoji", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateEmoji(@PathVariable Long categoryId, @RequestBody String newEmoji) {
        categoryService.updateEmoji(categoryId, newEmoji);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/name/{categoryId}/orders", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Void> updateOrders(@PathVariable Long categoryId, @RequestBody Integer newOrders) {
        categoryService.updateOrders(categoryId, newOrders);
        return ResponseEntity.ok().build();
    }
}
