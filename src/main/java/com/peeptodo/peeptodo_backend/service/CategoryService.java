package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    //Create
    public Category createCategory(CategoryRequestDto requestDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        Category category = new Category();
        category.setName(requestDto.getName());
        category.setColor(requestDto.getColor());
        category.setEmoji(requestDto.getEmoji());
        category.setOrders(requestDto.getOrders());
        category.setUser(user);
        return categoryRepository.save(category);
    }

    //Delete
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    //Read
    public List<CategoryResponseDto> getAllCategories(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        return categories.stream()
                .map(category -> {
                    CategoryResponseDto dto = new CategoryResponseDto();
                    dto.setId(category.getId());
                    dto.setName(category.getName());
                    dto.setColor(category.getColor());
                    dto.setEmoji(category.getEmoji());
                    dto.setOrders(category.getOrders());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //Update
    public void updateName(Long id, String newName) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        category.setName(newName);
        categoryRepository.save(category);
    }

    public void updateColor(Long id, String newColor) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        category.setColor(newColor);
        categoryRepository.save(category);
    }

    public void updateEmoji(Long id, String newEmoji) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        category.setEmoji(newEmoji);
        categoryRepository.save(category);
    }

    public void updateOrders(Long id, Integer newOrders) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        category.setOrders(newOrders);
        categoryRepository.save(category);
    }
}
