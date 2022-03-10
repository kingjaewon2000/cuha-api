package com.cju.cuhaapi.post;

import lombok.*;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private String title;
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private String title;
        private String content;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class PostResponse {
        private Long id;
        private String title;
        private String content;
        private Long views;
        private String username;
        private String createdAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class IdResponse {
        private Long id;
    }
}
