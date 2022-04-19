package com.cju.cuhaapi;

import com.cju.cuhaapi.member.dto.MemberDto.JoinRequest;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.cju.cuhaapi.member.domain.entity.Department.DIGITAL_SECURITY;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);

        JoinRequest request = JoinRequest.builder()
                .username("memberA")
                .password("memberA")
                .name("홍길동")
                .isMale(true)
                .email("test@test.com")
                .phoneNumber("010-1234-5678")
                .studentId("0000")
                .department(DIGITAL_SECURITY)
                .build();

        Member member = Member.join(request);
        em.persist(member);
    }

    @Test
    void querydsl() {
        QMember m = QMember.member;

        Member memberA = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("memberA"))
                .fetchOne();

        assertThat(memberA.getUsername()).isEqualTo("memberA");
        assertThat(memberA.getName()).isEqualTo("홍길동");
    }
}
