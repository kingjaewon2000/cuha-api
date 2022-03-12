package com.cju.cuhaapi.domain.post.dto;

import lombok.*;

public class CommentDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class SaveRequest {
        private String body;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private String body;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class CommentResponse {
        private Long id;
        private String body;
        private Long postId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class DeleteResponse {
        private String category;
        private Long postId;
        private Long id;
    }
}
