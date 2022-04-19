package com.cju.cuhaapi.member.domain.entity;

import com.cju.cuhaapi.commons.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column
    private String originalFilename;

    @Column
    private String filename;

    @ColumnDefault("0")
    @Column
    private Long size;

    //== 생성 메서드 ==//
    public static Profile createProfile(String originalFilename, String filename, Long size) {
        return Profile.builder()
                .originalFilename(originalFilename)
                .filename(filename)
                .size(size)
                .build();
    }
}
