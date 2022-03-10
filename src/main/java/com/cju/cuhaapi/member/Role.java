package com.cju.cuhaapi.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Entity
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @ColumnDefault("'ROLE_MEMBER'")
    @Column
    private String role;
}
