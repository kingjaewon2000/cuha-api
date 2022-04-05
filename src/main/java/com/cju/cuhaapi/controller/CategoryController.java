package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.controller.dto.CategoryDto.CreateRequest;
import com.cju.cuhaapi.controller.dto.CategoryDto.CategoryResponse;
import com.cju.cuhaapi.service.CategoryService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "카테고리 조회", notes = "카테고리를 조회합니다.")
    @GetMapping
    public List<CategoryResponse> categories() {
        return categoryService.getAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "카테고리 생성", notes = "카테고리를 생성합니다.")
    @PostMapping
    public void save(@RequestBody CreateRequest request) {
        categoryService.saveCategory(request);
    }
}
