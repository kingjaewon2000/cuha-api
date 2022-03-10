package com.cju.cuhaapi.post;

import com.cju.cuhaapi.common.BaseTime;
import com.cju.cuhaapi.post.CategoryDto.CategoryRequest;
import com.cju.cuhaapi.post.CategoryDto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @PostMapping
    public CategoryResponse create(@RequestBody CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .baseTime(new BaseTime())
                .build();

        Category savedCategory = categoryService.saveCategory(category);

        CategoryResponse categoryResponse = CategoryResponse.builder()
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();

        return categoryResponse;
    }
}
