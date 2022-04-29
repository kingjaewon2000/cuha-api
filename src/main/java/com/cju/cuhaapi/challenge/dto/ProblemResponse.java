package com.cju.cuhaapi.challenge.dto;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.entity.ProblemType;
import com.cju.cuhaapi.challenge.domain.entity.Tier;
import lombok.Getter;

@Getter
public class ProblemResponse {
    private Long problemId;
    private ProblemType problemType;
    private Tier tier;
    private String title;
    private String body;
    private Integer score;
    private String createdAt;

    public ProblemResponse(Problem problem) {
        this.problemId = problem.getId();
        this.problemType = problem.getType();
        this.tier = problem.getTier();
        this.title = problem.getTitle();
        this.body = problem.getBody();
        this.score = problem.getScore();
        this.createdAt = problem.getCreatedAt().toString();
    }
}
