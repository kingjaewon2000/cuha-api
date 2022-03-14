package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.post.dto.CategoryDto;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cju.cuhaapi.domain.post.dto.CategoryDto.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category notice = getNotice();
    private Category free = getFree();
    private Category question = getQuestion();


    @DisplayName("모든 카테고리를 조회하는 경우")
    @Test
    void 모든_카테고리() {
        List<Category> result = new ArrayList<>();

        given(categoryRepository.findAll()).willReturn(result);
        List<Category> categories1 = categoryService.findAll();
        assertEquals(0, categories1.size());

        result.addAll(List.of(notice, free, question));
        given(categoryRepository.findAll()).willReturn(result);

        List<Category> categories2 = categoryService.findAll();
        assertEquals(3, categories2.size());
    }

    @DisplayName("카테고리 이름에 해당하는 카테고리 조회")
    @Test
    void 카테고리_조회() {
        given(categoryRepository.findByName(notice.getName())).willReturn(Optional.ofNullable(notice));

        Category findCategory = categoryService.getCategory("notice");

        assertEquals(notice.getName(), findCategory.getName());
        assertEquals(notice.getDescription(), findCategory.getDescription());
    }

    @DisplayName("카테고리 조회시 해당하는 아이디가 없는 경우")
    @Test
    void 카테고리_조회_실패() {
        String name = "non-existent";

        given(categoryRepository.findByName(name)).willThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> categoryService.getCategory(name));
    }

    @DisplayName("카테고리 추가")
    @Test
    void 카테고리_추가() {
        CreateRequest request = CreateRequest.builder()
                .name("notice")
                .description("공지사항")
                .build();

        given(categoryRepository.save(any())).willReturn(notice);
        given(categoryRepository.findByName(notice.getName())).willReturn(Optional.ofNullable(notice));


        categoryService.saveCategory(request);
        Category findCategory = categoryService.getCategory(notice.getName());

        assertEquals(notice.getId(), findCategory.getId());
        assertEquals(notice.getName(), findCategory.getName());
        assertEquals(notice.getDescription(), findCategory.getDescription());
    }

    private Category getNotice() {
        Category category = Category.builder()
                .id(1L)
                .name("notice")
                .description("공지사항")
                .build();

        return category;
    }

    private Category getFree() {
        Category category = Category.builder()
                .id(2L)
                .name("free")
                .description("자유게시판")
                .build();

        return category;
    }

    private Category getQuestion() {
        Category category = Category.builder()
                .id(3L)
                .name("question")
                .description("질문게시판")
                .build();

        return category;
    }



}