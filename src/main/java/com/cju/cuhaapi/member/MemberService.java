package com.cju.cuhaapi.member;

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

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public Member saveMember(Member member) {
        // 아이디 중복 체크
        String username = member.getUsername();
        if(isDuplicateUsername(username)) {
            throw new DuplicateUsernameException("아이디가 중복되었습니다.");
        }

        // 회원가입 로직
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public Member updateMember(Member member) {
        // 멤버 정보 변경 및 저장
        Member updatedMember = memberRepository.save(member);

        return updatedMember;
    }

    public Member updatePassword(Member member) {
        // 비밀번호 변경 및 저장
        Member updatedMember = memberRepository.save(member);

        return updatedMember;
    }

    public Member deleteMember(Long id) {
        // ID값으로 멤버 가져오기.
        Member findMember = getMember(id);

        // 회원 탈퇴
        memberRepository.delete(findMember);

        return findMember;
    }

    /**
     * 도우미 메서드
     */
    public boolean isDuplicateUsername(String username) {
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            return false;
        }

        return true;
    }
}
