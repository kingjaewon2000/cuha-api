package com.cju.cuhaapi.post.dto;

import com.cju.cuhaapi.post.domain.entity.Category;
import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryResponse {

    private String categoryName;
    private String description;

    public CategoryResponse(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}
