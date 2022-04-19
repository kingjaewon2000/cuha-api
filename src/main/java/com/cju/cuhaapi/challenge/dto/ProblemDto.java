package com.cju.cuhaapi.challenge.dto;

import com.cju.cuhaapi.challenge.domain.entity.Problem;
import com.cju.cuhaapi.challenge.domain.entity.ProblemType;
import com.cju.cuhaapi.challenge.domain.entity.Tier;
import lombok.*;

public class ProblemDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private ProblemType problemType;
        private Tier tier;
        private String title;
        private String body;
        private Integer score;
        private String flag;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private ProblemType problemType;
        private Tier tier;
        private String title;
        private String body;
        private Integer score;
        private String flag;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class SubmitRequest {
        private String flag;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProblemResponse {
        private Long id;
        private ProblemType problemType;
        private Tier tier;
        private String title;
        private String body;
        private Integer score;
        private String createdAt;

        public static ProblemResponse of(Problem problem) {
            return ProblemResponse.builder()
                    .id(problem.getId())
                    .problemType(problem.getType())
                    .tier(problem.getTier())
                    .title(problem.getTitle())
                    .body(problem.getBody())
                    .score(problem.getScore())
                    .createdAt(problem.getCreatedAt().toString())
                    .build();
        }
    }


}
