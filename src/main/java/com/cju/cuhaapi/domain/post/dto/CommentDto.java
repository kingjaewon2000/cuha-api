package com.cju.cuhaapi.domain.post.dto;

import com.cju.cuhaapi.domain.post.entity.Comment;
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
        private String category;
        private Long postId;
        private Long id;
        private String body;
        private Long like;
        private String username;
        private String name;
        private String profileImage;
        private String createdAt;

        public static CommentResponse of(Comment comment, Long like) {
            return CommentResponse.builder()
                    .category(comment.getPost().getCategory().getName())
                    .postId(comment.getPost().getId())
                    .id(comment.getId())
                    .body(comment.getBody())
                    .like(like)
                    .username(comment.getMember().getUsername())
                    .name(comment.getMember().getName())
                    .profileImage(comment.getMember().getProfile().getFilename())
                    .createdAt(comment.getBaseTime().getCreatedAt().toString())
                    .build();
        }
    }

}
