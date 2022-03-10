package com.cju.cuhaapi.post;

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
        private String name;
        private String description;
    }
}
