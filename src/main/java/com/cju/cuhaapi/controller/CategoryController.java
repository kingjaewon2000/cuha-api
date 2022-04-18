package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.controller.dto.CategoryDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.CategoryDto.CategoryResponse;
import com.cju.cuhaapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<CategoryResponse> categories() {
        return categoryService.getAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void save(@RequestBody CreateRequest request) {
        categoryService.saveCategory(request);
    }
}
