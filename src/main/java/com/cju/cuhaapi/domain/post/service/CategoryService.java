package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.post.dto.CategoryDto.CreateRequest;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll(Sort.by("id").descending());
    }

    public Category getCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name값이 잘못 지정되었습니다."));
    }

    public void saveCategory(CreateRequest request) {
        Category category = Category.createCategory(request);

        categoryRepository.save(category);
    }
}
