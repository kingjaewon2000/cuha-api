package com.cju.cuhaapi.post.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostRequest {

    private String title;
    private String body;
}
