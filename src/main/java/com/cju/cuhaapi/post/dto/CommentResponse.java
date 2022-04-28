package com.cju.cuhaapi.post.dto;

import com.cju.cuhaapi.post.domain.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {

    private String categoryName;
    private Long postId;
    private Long commentId;
    private String body;
    private Long like;
    private String username;
    private String name;
    private String profileImage;
    private String createdAt;

    public CommentResponse(Comment comment) {
        this.categoryName = comment.getPost().getCategory().getName();
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.body = comment.getBody();
        this.like = 0L;;
        this.username = comment.getMember().getUsername();
        this.name = comment.getMember().getName();
        this.profileImage = comment.getMember().getProfile().getFilename();
        this.createdAt = comment.getCreatedAt().toString();
    }


}
