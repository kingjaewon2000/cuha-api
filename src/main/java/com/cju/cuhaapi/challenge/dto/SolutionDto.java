package com.cju.cuhaapi.challenge.dto;

import com.cju.cuhaapi.challenge.domain.entity.Solution;
import lombok.*;

public class SolutionDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private String body;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private String body;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class SolutionResponse {
        private String body;

        public static SolutionResponse of(Solution solution) {
            return SolutionResponse.builder()
                    .body(solution.getBody())
                    .build();
        }
    }
}
