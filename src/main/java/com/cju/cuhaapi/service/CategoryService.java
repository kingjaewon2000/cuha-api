package com.cju.cuhaapi.service;

import com.cju.cuhaapi.controller.dto.CategoryDto.CreateRequest;
import com.cju.cuhaapi.entity.post.Category;
import com.cju.cuhaapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll(Sort.by("id").descending());
    }

    public Category getCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("name값이 잘못 지정되었습니다."));
    }

    public void saveCategory(CreateRequest request) {
        Category category = Category.createCategory(request);

        categoryRepository.save(category);
    }
}
