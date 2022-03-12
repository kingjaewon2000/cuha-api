package com.cju.cuhaapi.domain.post.dto;

import lombok.*;

public class CategoryDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CategoryRequest {
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
    }
}
