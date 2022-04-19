package com.cju.cuhaapi.challenge.domain.entity;

public enum Tier {
    BRONZE("브론즈"), SILVER("실버"), GOLD("골드"),
    PLATINUM("플래티넘"), DIAMOND("다이아몬드");

    private String description;

    Tier(String description) {
        this.description = description;
    }
}
