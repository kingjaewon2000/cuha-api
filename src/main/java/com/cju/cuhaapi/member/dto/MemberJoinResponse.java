package com.cju.cuhaapi.member.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberJoinResponse {

    private Long id;
    private String username;

    public MemberJoinResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
