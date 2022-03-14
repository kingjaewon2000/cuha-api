package com.cju.cuhaapi.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column
    private String originalFilename;

    @Column
    private String filename;

    @ColumnDefault("0")
    @Column
    private Long size;

    public static Profile of(String originalFilename, String filename, Long size) {
        return Profile.builder()
                .originalFilename(originalFilename)
                .filename(filename)
                .size(size)
                .build();
    }
}
