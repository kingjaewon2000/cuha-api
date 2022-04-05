package com.cju.cuhaapi.controller.dto;

import com.cju.cuhaapi.repository.entity.post.Post;
import lombok.*;

public class PostDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class SaveRequest {
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
        private String category;
        private Long id;
        private String title;
        private String body;
        private Long views;
        private Long like;
        private String createdAt;
        private String profileImage;
        private String username;
        private String name;

        public static PostResponse of(Post post, Long like) {
            return PostResponse.builder()
                    .category(post.getCategory().getName())
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .views(post.getViews())
                    .like(like)
                    .createdAt(post.getBaseTime().getCreatedAt().toString())
                    .profileImage(post.getMember().getProfile().getFilename())
                    .username(post.getMember().getUsername())
                    .name(post.getMember().getName())
                    .build();
        }
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
