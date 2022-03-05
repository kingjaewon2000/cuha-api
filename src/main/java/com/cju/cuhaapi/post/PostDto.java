package com.cju.cuhaapi.post;

import lombok.*;

import java.time.LocalDateTime;

public class PostDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GetResponse {
        private Long id;
        private String title;
        private String content;
        private Long views;
        private String username;
        private String createdAt;
    }
}
