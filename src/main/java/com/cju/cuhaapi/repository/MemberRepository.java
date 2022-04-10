package com.cju.cuhaapi.repository;

import com.cju.cuhaapi.controller.dto.MemberDto;
import com.cju.cuhaapi.controller.dto.MemberDto.RankingResponse;
import com.cju.cuhaapi.repository.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQuery;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
    boolean existsByUsername(String username);

    @Query(value = "SELECT ranking FROM ( select member_id, total_score, rank() over (order by total_score desc) as 'ranking' from Member) ranked where member_id = :memberId limit 1;",
            nativeQuery = true)
    Long ranking(@Param("memberId") Long memberId);
}
