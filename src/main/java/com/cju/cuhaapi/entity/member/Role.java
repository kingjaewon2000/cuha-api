package com.cju.cuhaapi.entity.member;

import com.cju.cuhaapi.entity.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Role extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String description;
}
