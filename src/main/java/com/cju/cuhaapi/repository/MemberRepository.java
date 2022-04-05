package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.repository.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
    boolean existsByUsername(String username);
}
