package com.cju.cuhaapi.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("name값이 잘못 지정되었습니다."));
    }

    public Category saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);

        return savedCategory;
    }
}
