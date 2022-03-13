package com.cju.cuhaapi.domain.member.repository;

import com.cju.cuhaapi.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUsername(String username);
}