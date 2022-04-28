package com.cju.cuhaapi.post.controller;

import com.cju.cuhaapi.post.dto.CategoryCreateRequest;
import com.cju.cuhaapi.post.dto.CategoryResponse;
import com.cju.cuhaapi.post.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> categories(Sort sort) {
        return categoryService.findAll(sort)
                .stream()
                    .map(category -> new CategoryResponse(
                            category.getName(),
                            category.getDescription()
                    ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void create(@RequestBody CategoryCreateRequest request) {
        categoryService.createCategory(request);
    }

    @DeleteMapping("/{categoryName}")
    public void delete(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
    }
}
