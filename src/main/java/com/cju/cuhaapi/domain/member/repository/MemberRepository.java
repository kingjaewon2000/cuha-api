package com.cju.cuhaapi.domain.member.repository;

import com.cju.cuhaapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
    boolean existsByUsername(String username);
}
