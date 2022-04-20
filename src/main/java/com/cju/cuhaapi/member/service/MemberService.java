package com.cju.cuhaapi.member.service;

import com.cju.cuhaapi.member.domain.entity.Role;
import com.cju.cuhaapi.member.domain.repository.ProfileRepository;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Password;
import com.cju.cuhaapi.member.domain.entity.Profile;
import com.cju.cuhaapi.member.domain.repository.MemberRepository;
import com.cju.cuhaapi.commons.error.exception.DuplicateUsernameException;
import com.cju.cuhaapi.commons.utils.PasswordEncoderUtils;
import com.cju.cuhaapi.member.domain.repository.RoleRepository;
import com.cju.cuhaapi.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾지 못했습니다."));
    }

    public List<MemberResponse> findMembers(Pageable pageable) {
        return memberRepository.findMembers(pageable);
    }

    public MemberResponse findMember(String username) {
        return memberRepository.findMember(username);
    }

    public MemberInfoResponse myInfo(Long id) {
        return memberRepository.memberInfo(id);
    }

    public MemberRankInfoResponse myRank(Long id) {
        return memberRepository.memberRank(id);
    }

    @Transactional
    public MemberJoinResponse saveMember(MemberJoinRequest request) {
        Role role = roleRepository.defaultRole();
        Profile profile = profileRepository.defaultProfile();
        Member member = Member.join(request, role, profile);

        // 아이디 중복 체크
        String username = member.getUsername();
        if(isDuplicateUsername(username)) {
            throw new DuplicateUsernameException("아이디가 중복되었습니다.");
        }

        // 회원가입 로직
        Member savedMember = memberRepository.save(member);

        return new MemberJoinResponse(savedMember.getId(), savedMember.getUsername());
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateInfoRequest request, Profile uploadProfile) {
        if (uploadProfile != null && !profileRepository.existsByFilename(uploadProfile.getFilename())) {
            profileRepository.save(uploadProfile);
        }

        Member member = findById(memberId);

        member.updateMember(request, uploadProfile);
    }

    @Transactional
    public void updatePassword(Long memberId, MemberUpdatePasswordRequest request) {
        String passwordBefore = request.getPasswordBefore();
        Member member = findById(memberId);

        // 비밀번호 검증
        String encodedPassword = member.getPassword().getValue();
        if (!isValidPassword(passwordBefore, encodedPassword)) {
            throw new IllegalStateException("이전 패스워드가 일치하지 않습니다.");
        }

        Password password = member.getPassword();
        password.updatePassword(request.getPasswordAfter());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        // 회원 탈퇴
        memberRepository.deleteById(memberId);
    }

    public boolean isDuplicateUsername(String username) {
        return memberRepository.existsByUsername(username);
    }


    private boolean isValidPassword(String passwordBefore, String encodedPassword) {
        return PasswordEncoderUtils.getInstance().matchers(passwordBefore, encodedPassword);
    }
}
