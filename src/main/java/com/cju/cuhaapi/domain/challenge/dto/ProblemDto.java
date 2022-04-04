package com.cju.cuhaapi.domain.challenge.dto;

import com.cju.cuhaapi.domain.challenge.entity.Problem;
import com.cju.cuhaapi.domain.challenge.entity.ProblemType;
import com.cju.cuhaapi.domain.challenge.entity.Tear;
import lombok.*;

public class ProblemDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private ProblemType problemType;
        private Tear tear;
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
        private Tear tear;
        private String title;
        private String body;
        private Integer score;
        private String flag;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class ProblemResponse {
        private Long id;
        private ProblemType problemType;
        private Tear tear;
        private String title;
        private String body;
        private Integer score;
        private String createdAt;

        public static ProblemResponse of(Problem problem) {
            return ProblemResponse.builder()
                    .id(problem.getId())
                    .problemType(problem.getType())
                    .tear(problem.getTear())
                    .title(problem.getTitle())
                    .body(problem.getBody())
                    .score(problem.getScore())
                    .createdAt(problem.getBaseTime().getCreatedAt().toString())
                    .build();
        }
    }


}
