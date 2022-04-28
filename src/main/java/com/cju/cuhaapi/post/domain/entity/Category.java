package com.cju.cuhaapi.post.domain.entity;

import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import com.cju.cuhaapi.post.dto.CategoryCreateRequest;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    //== 생성 메서드 ==//
    public static Category createCategory(CategoryCreateRequest request) {
        return Category.builder()
                .name(request.getCategoryName())
                .description(request.getDescription())
                .build();
    }
}
