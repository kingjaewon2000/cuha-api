package com.cju.cuhaapi;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Profile;
import com.cju.cuhaapi.member.domain.entity.Role;
import com.cju.cuhaapi.member.domain.repository.ProfileRepository;
import com.cju.cuhaapi.member.domain.repository.RoleRepository;
import com.cju.cuhaapi.member.dto.MemberJoinRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.cju.cuhaapi.member.domain.entity.Department.DIGITAL_SECURITY;
import static com.cju.cuhaapi.member.domain.entity.Gender.MALE;

@SpringBootTest
@Transactional
public class QueryDslTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);

        MemberJoinRequest request = MemberJoinRequest.builder()
                .username("memberA")
                .password("memberA")
                .name("홍길동")
                .gender(MALE)
                .email("test@test.com")
                .phoneNumber("010-1234-5678")
                .studentId("0000")
                .department(DIGITAL_SECURITY)
                .build();

        Role role = roleRepository.defaultRole();
        Profile profile = profileRepository.defaultProfile();


        Member member = Member.join(request, role, profile);
        em.persist(member);
    }

}
