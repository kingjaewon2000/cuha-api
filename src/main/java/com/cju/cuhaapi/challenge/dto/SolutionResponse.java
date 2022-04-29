package com.cju.cuhaapi.challenge.dto;

import com.cju.cuhaapi.challenge.domain.entity.Solution;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class SolutionResponse {
    private String body;

    public SolutionResponse(Solution solution) {
        this.body = solution.getBody();
    }
}
