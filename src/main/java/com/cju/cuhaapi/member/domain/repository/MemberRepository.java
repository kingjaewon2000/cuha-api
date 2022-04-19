package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT m FROM Member m join fetch m.role join fetch m.profile where m.username = :username")
    List<Member> findByUsername(@Param("username") String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT ranking FROM ( select member_id, total_score, rank() over (order by total_score desc) as 'ranking' from Member) ranked where member_id = :memberId limit 1;",
            nativeQuery = true)
    Long ranking(@Param("memberId") Long memberId);
}
