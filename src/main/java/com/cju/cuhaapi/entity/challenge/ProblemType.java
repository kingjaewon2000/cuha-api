package com.cju.cuhaapi.entity.challenge;

public enum ProblemType {
    FORENSIC("포렌식"),
    MISC("기타"),
    REVERSING("리버싱"),
    SYSTEM("시스템해킹"),
    WEB("웹해킹");

    private String description;

    ProblemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
