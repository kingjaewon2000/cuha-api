package com.cju.cuhaapi.post;

import com.cju.cuhaapi.common.BaseTime;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Embedded
    private BaseTime baseTime;
}
