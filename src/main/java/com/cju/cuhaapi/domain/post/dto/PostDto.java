package com.cju.cuhaapi.domain.post.dto;

import lombok.*;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private String title;
        private String body;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private String title;
        private String body;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class PostResponse {
        private Long id;
        private String title;
        private String body;
        private Long views;
        private String username;
        private String name;
        private String createdAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class DeleteResponse {
        private String category;
        private Long id;
    }
}
