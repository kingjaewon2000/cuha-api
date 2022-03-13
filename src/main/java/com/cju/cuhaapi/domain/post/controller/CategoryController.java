package com.cju.cuhaapi.domain.post.controller;

import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.dto.CategoryDto.CategoryRequest;
import com.cju.cuhaapi.domain.post.dto.CategoryDto.CategoryResponse;
import com.cju.cuhaapi.domain.post.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.cju.cuhaapi.mapper.CategoryMapper.INSTANCE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 조회", notes = "카테고리를 조회합니다.")
    @GetMapping
    public List<CategoryResponse> categories() {
        return categoryService.findAll()
                .stream()
                .map(INSTANCE::entityToCategoryResponse)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "카테고리 생성", notes = "카테고리를 생성합니다.")
    @PostMapping
    public CategoryResponse create(@RequestBody CategoryRequest categoryRequest) {
        Category category = INSTANCE.categoryRequestToEntity(categoryRequest);
        categoryService.saveCategory(category);
        CategoryResponse response = INSTANCE.entityToCategoryResponse(category);

        return response;
    }
}
