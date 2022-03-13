package com.cju.cuhaapi.domain.member.service;

import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.repository.ProfileRepository;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Profile;
import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.error.exception.DuplicateUsernameException;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.cju.cuhaapi.mapper.MemberMapper.INSTANCE;

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
        if (profile == null) {
            throw new IllegalArgumentException("프로필이 존재하지 않습니다.");
        }

        if (!profileRepository.existsByFilename(profile.getFilename())) {
            profileRepository.save(profile);
        }

        memberRepository.save(member);
    }

    public Member updatePassword(Long id, String passwordBefore, String passwordAfter) {
        Member findMember = findMember(id);

        // 비밀번호 검증
        String encodedPassword = findMember.getPassword().getValue();
        if (!isValidPassword(passwordBefore, encodedPassword)) {
            throw new IllegalStateException("이전 패스워드가 일치하지 않습니다.");
        }

        // 비밀번호 변경
        Member member = INSTANCE.updatePasswordRequestToEntity(passwordAfter, findMember);
        return memberRepository.save(member);
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


    private boolean isValidPassword(String passwordBefore, String encodedPassword) {
        return PasswordEncoderUtils.getInstance().matchers(passwordBefore, encodedPassword);
    }
}
