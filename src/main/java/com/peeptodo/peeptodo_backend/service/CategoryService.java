package com.peeptodo.peeptodo_backend.service;

import com.peeptodo.peeptodo_backend.domain.Category;
import com.peeptodo.peeptodo_backend.domain.Todo;
import com.peeptodo.peeptodo_backend.domain.User;
import com.peeptodo.peeptodo_backend.dto.CategoryRequestDto;
import com.peeptodo.peeptodo_backend.dto.CategoryResponseDto;
import com.peeptodo.peeptodo_backend.exception.CategoryRemoveException;
import com.peeptodo.peeptodo_backend.repository.CategoryRepository;
import com.peeptodo.peeptodo_backend.repository.UserRepository;
import com.peeptodo.peeptodo_backend.util.Utils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements OrdersService{

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

        Integer orders = requestDto.getOrders();
        if (orders == null) {
            orders = getNextOrders(user.getId());
        }

        Category category = new Category();
        category.setName(requestDto.getName());
        category.setColor(requestDto.getColor());
        category.setEmoji(requestDto.getEmoji());
        category.setOrders(orders);
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

    public void swapOrders(Long categoryId, Long swapCategoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));
        Category swapCategory = categoryRepository.findById(swapCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));


        if (category.equals(swapCategory)) return;

        // 카테고리가 다른 유저의 카테고리를 가리킬 때
        User user1 = (User) Utils.unproxy(category.getUser());
        User user2 = (User) Utils.unproxy(swapCategory.getUser());
        if (!user1.equals(user2)) {
            throw new IllegalArgumentException("User in Category not match!");
        }

        Integer o1 = category.getOrders();
        Integer o2 = swapCategory.getOrders();

        category.setOrders(o2);
        swapCategory.setOrders(o1);
        categoryRepository.saveAll(List.of(category, swapCategory)); // 동일한 트랜잭션으로 처리해서 하나가 오류나면 둘다 오류가 발생해야 함
    }

    @Override
    public int getNextOrders(Long userId) {
        Optional<Category> maxCategory = categoryRepository.findFirstByUserIdOrderByOrdersDesc(userId);
        return maxCategory.map(category -> category.getOrders() + 1).orElse(1);
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
