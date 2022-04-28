package com.cju.cuhaapi.member.dto;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {

    private String username;
    private String name;
    private int score;
    private String profileUrl;

    public MemberResponse(Member member) {
        this.username = member.getUsername();
        this.name = member.getName();
        this.score = member.getScore();
        this.profileUrl = member.getProfile().getFilename();
    }
}
