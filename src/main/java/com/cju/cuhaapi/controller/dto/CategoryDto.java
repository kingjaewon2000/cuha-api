package com.cju.cuhaapi.controller.dto;

import com.cju.cuhaapi.entity.post.Category;
import lombok.*;

public class CategoryDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private String name;
        private String description;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class CategoryResponse {
        private Long id;
        private String name;
        private String description;

        public static CategoryResponse of(Category category) {
            return CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();
        }
    }
}
