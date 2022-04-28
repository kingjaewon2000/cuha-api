package com.cju.cuhaapi.post.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostDeleteResponse {

    private String categoryName;
    private Long postId;

}
