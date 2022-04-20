package com.cju.cuhaapi.member.domain.entity;

public enum Gender {

    MALE("남자"), FEMALE("여자");

    private String description;

    Gender(String description) {
        this.description = description;
    }
}
