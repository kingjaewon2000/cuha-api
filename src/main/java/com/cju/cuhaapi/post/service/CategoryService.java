package com.cju.cuhaapi.post.service;

import com.cju.cuhaapi.post.domain.entity.Category;
import com.cju.cuhaapi.post.domain.repository.CategoryRepository;
import com.cju.cuhaapi.post.dto.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    public Category findCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 옳바르지 않습니다."));
    }

    public void createCategory(CategoryCreateRequest request) {
        Category category = Category.createCategory(request);

        categoryRepository.save(category);
    }

    public void deleteCategory(String categoryName) {
        Category category = findCategory(categoryName);
        categoryRepository.delete(category);
    }
}
