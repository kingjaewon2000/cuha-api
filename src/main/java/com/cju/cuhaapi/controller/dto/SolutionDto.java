package com.cju.cuhaapi.controller.dto;

import com.cju.cuhaapi.repository.entity.challenge.Solution;
import lombok.*;

public class SolutionDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class CreateRequest {
        private String title;
        private String body;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {
        private String title;
        private String body;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class SolutionResponse {
        private String title;
        private String body;

        public static SolutionResponse of(Solution solution) {
            return SolutionResponse.builder()
                    .title(solution.getTitle())
                    .body(solution.getBody())
                    .build();
        }
    }
}
