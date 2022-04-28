package com.cju.cuhaapi.post.dto;

import com.cju.cuhaapi.post.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostResponse {

    private String categoryName;
    private Long postId;
    private String title;
    private String body;
    private long views;
    private long like;
    private String createdAt;
    private String username;
    private String name;
    private String profileUrl;

    public PostResponse(Post post) {
        this.categoryName = post.getCategory().getName();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.views = post.getViews();
        this.like = 0L;
        this.createdAt = post.getCreatedAt().toString();
        this.username = post.getMember().getUsername();
        this.name = post.getMember().getName();
        this.profileUrl = post.getMember().getProfile().getFilename();
    }
}
