package com.cju.cuhaapi.domain.member.service;

import com.cju.cuhaapi.domain.member.repository.ProfileRepository;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Profile;
import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.error.exception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public void saveMember(Member member) {
        // 아이디 중복 체크
        String username = member.getUsername();
        if(isDuplicateUsername(username)) {
            throw new DuplicateUsernameException("아이디가 중복되었습니다.");
        }

        // 회원가입 로직
        memberRepository.save(member);
    }

    public void updateMember(Member member) {
        Profile profile = member.getProfile();
        if (profile != null && !profileRepository.existsByFilename(profile.getFilename())) {
            profileRepository.save(profile);
        }

        memberRepository.save(member);
    }

    public void updatePassword(Member member) {
        // 비밀번호 변경 및 저장
        memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        // 회원 탈퇴
        memberRepository.delete(member);
    }

    /**
     * 도우미 메서드
     */
    public boolean isDuplicateUsername(String username) {
        return memberRepository.existsByUsername(username);
    }
}
