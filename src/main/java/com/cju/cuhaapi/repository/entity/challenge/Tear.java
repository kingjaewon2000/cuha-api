package com.cju.cuhaapi.repository.entity.challenge;

public enum Tear {
    BRONZE("브론즈"), SILVER("실버"), GOLD("골드"),
    PLATINUM("플래티넘"), DIAMOND("다이아몬드");

    private String description;

    Tear(String description) {
        this.description = description;
    }
}
