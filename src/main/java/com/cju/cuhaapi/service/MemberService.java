package com.cju.cuhaapi.service;

import com.cju.cuhaapi.repository.ProfileRepository;
import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.repository.entity.member.Profile;
import com.cju.cuhaapi.repository.MemberRepository;
import com.cju.cuhaapi.error.exception.DuplicateUsernameException;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.cju.cuhaapi.controller.dto.MemberDto.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public Page<Member> getMembers(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end, Sort.by("id").descending());

        return memberRepository.findAll(pageRequest);
    }

    public Page<Member> getMembersOrderByScore(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end, Sort.by("totalScore").descending());

        return memberRepository.findAll(pageRequest);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID값이 잘못 지정되었습니다."));
    }

    public void saveMember(JoinRequest request) {
        Member member = Member.join(request);

        // 아이디 중복 체크
        String username = member.getUsername();
        if(isDuplicateUsername(username)) {
            throw new DuplicateUsernameException("아이디가 중복되었습니다.");
        }

        // 회원가입 로직
        memberRepository.save(member);
    }

    public void updateMember(UpdateInfoRequest request, Member currentMember, Profile uploadProfile) {
        Member member = Member.updateInfo(request, currentMember, uploadProfile);

        if (uploadProfile != null && !profileRepository.existsByFilename(uploadProfile.getFilename())) {
            profileRepository.save(uploadProfile);
        }

        memberRepository.save(member);
    }

    public void updatePassword(UpdatePasswordRequest request, Member currentMember) {
        String password = request.getPasswordBefore();

        // 비밀번호 검증
        String encodedPassword = currentMember.getPassword().getValue();
        if (!isValidPassword(password, encodedPassword)) {
            throw new IllegalStateException("이전 패스워드가 일치하지 않습니다.");
        }

        Member member = Member.updatePassword(request, currentMember);

        memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        // 회원 탈퇴
        memberRepository.delete(member);
    }

    public Long ranking(Long id) {
        return memberRepository.ranking(id);
    }

    //== 도우미 메서드 ==//
    public boolean isDuplicateUsername(String username) {
        return memberRepository.existsByUsername(username);
    }


    private boolean isValidPassword(String passwordBefore, String encodedPassword) {
        return PasswordEncoderUtils.getInstance().matchers(passwordBefore, encodedPassword);
    }
}
