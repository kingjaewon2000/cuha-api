package com.cju.cuhaapi.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberRankInfoResponse {

    private String username;
    private String name;
    private int score;
    private String roleDescription;
    private String profileUrl;

    private int rank;

    @QueryProjection
    public MemberRankInfoResponse(String username, String name, int score, String roleDescription, String profileUrl, int rank) {
        this.username = username;
        this.name = name;
        this.score = score;
        this.roleDescription = roleDescription;
        this.profileUrl = profileUrl;
        this.rank = rank;
    }
}
