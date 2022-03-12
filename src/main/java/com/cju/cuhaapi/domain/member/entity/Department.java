package com.cju.cuhaapi.domain.member.entity;

import lombok.Getter;

@Getter
public enum Department {
    BIG_DATA("빅데이터전공"),
    AI("인공지능전공"),
    DIGITAL_SECURITY("디지털보안전공");

    private final String description;

    Department(String description) {
        this.description = description;
    }
}
