package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Category createCategory(CategoryRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
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

    /**
     * 인증된 ID에 해당하는 카테고리들 전부 GET
     * @return List<CategoryResponseDto>
     */
    public List<CategoryResponseDto> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        List<Category> categories = categoryRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Category not found!"));
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
