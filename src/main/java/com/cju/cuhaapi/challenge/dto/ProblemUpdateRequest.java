package com.cju.cuhaapi.challenge.dto;

import com.cju.cuhaapi.challenge.domain.entity.ProblemType;
import com.cju.cuhaapi.challenge.domain.entity.Tier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProblemUpdateRequest {
    private ProblemType problemType;
    private Tier tier;
    private String title;
    private String body;
    private Integer score;
    private String flag;
}
