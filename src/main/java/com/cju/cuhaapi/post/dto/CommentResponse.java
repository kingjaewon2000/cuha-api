package com.cju.cuhaapi.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommentResponse {

    private String categoryName;
    private Long postId;
    private Long id;
    private String body;
    private Long like;
    private String username;
    private String name;
    private String profileImage;
    private String createdAt;
}
