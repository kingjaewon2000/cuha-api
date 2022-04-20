package com.cju.cuhaapi.member.domain.repository;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.dto.MemberInfoResponse;
import com.cju.cuhaapi.member.dto.MemberRankInfoResponse;
import com.cju.cuhaapi.member.dto.MemberResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberQueryRepository {

    MemberInfoResponse memberInfo(Long id);
    MemberRankInfoResponse memberRank(Long id);

    Member findByUsername(String username);
    MemberResponse findMember(String username);

    List<MemberResponse> findMembers(Pageable pageable);

}
