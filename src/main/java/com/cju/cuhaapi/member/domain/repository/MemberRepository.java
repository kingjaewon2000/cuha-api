package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    boolean existsByUsername(String username);

}
