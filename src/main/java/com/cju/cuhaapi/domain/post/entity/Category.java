package com.cju.cuhaapi.domain.post.entity;

import com.cju.cuhaapi.audit.AuditListener;
import com.cju.cuhaapi.audit.Auditable;
import com.cju.cuhaapi.audit.BaseTime;
import com.cju.cuhaapi.domain.post.dto.CategoryDto.CreateRequest;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@EntityListeners(AuditListener.class)
public class Category implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Embedded
    private BaseTime baseTime;

    @Override
    public void setBaseTime(BaseTime baseTime) {
        this.baseTime = baseTime;
    }

    //== 생성 메서드 ==//
    public static Category createCategory(CreateRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
