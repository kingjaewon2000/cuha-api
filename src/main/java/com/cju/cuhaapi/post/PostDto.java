package com.cju.cuhaapi.post;

import lombok.*;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class CreateRequest {
        private String title;
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class PostResponse {
        private Long id;
        private String title;
        private String content;
        private Long views;
        private String username;
        private String createdAt;
    }
}
