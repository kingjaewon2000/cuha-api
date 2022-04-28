package com.cju.cuhaapi.post.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CategoryCreateRequest {

    private String categoryName;
    private String description;
}
