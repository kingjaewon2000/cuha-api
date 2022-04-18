package com.cju.cuhaapi.entity.post;

import com.cju.cuhaapi.controller.dto.CategoryDto.CreateRequest;
import com.cju.cuhaapi.entity.common.BaseTimeEntity;
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
    public static Category createCategory(CreateRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
