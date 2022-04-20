package com.cju.cuhaapi.member.dto;

import com.cju.cuhaapi.post.domain.entity.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberResponse {

    private String username;
    private String name;
    private int score;
    private String profileUrl;

    @QueryProjection
    public MemberResponse(String username, String name, int score, String profileUrl) {
        this.username = username;
        this.name = name;
        this.score = score;
        this.profileUrl = profileUrl;
    }
}
