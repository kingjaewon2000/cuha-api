package com.cju.cuhaapi.domain.member.service;

import com.cju.cuhaapi.controller.dto.MemberDto.JoinRequest;
import com.cju.cuhaapi.controller.dto.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.controller.dto.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.repository.entity.member.Department;
import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.repository.entity.member.Password;
import com.cju.cuhaapi.repository.entity.member.Profile;
import com.cju.cuhaapi.repository.MemberRepository;
import com.cju.cuhaapi.repository.ProfileRepository;
import com.cju.cuhaapi.error.exception.DuplicateUsernameException;
import com.cju.cuhaapi.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member = memberBuild();

    private JoinRequest joinRequest = joinRequestBuild();

    private Member memberBuild() {
        Member member = Member.builder()
                .id(1L)
                .username("cuha")
                .password(new Password("cuha"))
                .name("김태형")
                .isMale(true)
                .email("cuha@cju.ac.kr")
                .phoneNumber("010-1234-5678")
                .studentId("20220001")
                .department(Department.DIGITAL_SECURITY)
                .build();

        return member;
    }

    @DisplayName("아이디가 중복이 아닌 경우")
    @Test
    void 멤버_아이디_중복X() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(false);

        boolean isDuplicateUsername = memberService.isDuplicateUsername(member.getUsername());
        assertEquals(false, isDuplicateUsername);
    }

    @DisplayName("아이디가 중복인 경우")
    @Test
    void 멤버_아이디_중복O() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(true);

        assertEquals(true, memberService.isDuplicateUsername(member.getUsername()));
    }

    @DisplayName("멤버 조회")
    @Test
    void 멤버_조회() {
        given(memberRepository.save(any())).willReturn(member);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        memberService.saveMember(joinRequest);
        Member findMember = memberService.getMember(1L);

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
    }

    private JoinRequest joinRequestBuild() {
        JoinRequest request = JoinRequest.builder()
                .username("cuha")
                .password("cuha")
                .name("김태형")
                .isMale(true)
                .email("cuha@cju.ac.kr")
                .phoneNumber("010-1234-5678")
                .studentId("20220001")
                .department(Department.DIGITAL_SECURITY)
                .build();

        return request;
    }

    @DisplayName("멤버 조회시 해당하는 아이디가 없는 경우")
    @Test
    void 멤버_조회_실패() {
        Long memberId = 1L;

        given(memberRepository.findById(memberId)).willThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                () -> memberService.getMember(memberId));
    }

    @DisplayName("회원가입 할때 아이디가 중복인 경우")
    @Test
    void 멤버_회원가입_중복O() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(true);

        assertThrows(DuplicateUsernameException.class,
                () -> memberService.saveMember(joinRequest));
    }

    @DisplayName("회원가입 할때 아이디가 중복이 아닌 경우")
    @Test
    void 멤버_회원가입_중복X() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(false);

        assertDoesNotThrow(() -> memberService.saveMember(joinRequest));
    }

    @DisplayName("내 정보를 변경하는 경우(프로필 NULL)")
    @Test
    void 내_정보_변경_프로필_NULL() {
        UpdateInfoRequest request = updateInfoRequestBuild();
        Profile profile = null;

        assertDoesNotThrow(() -> memberService.updateMember(request, member, profile));
    }

    @DisplayName("내 정보를 변경하는 경우(프로필 포함 O)")
    @Test
    void 내_정보_변경() {
        UpdateInfoRequest request = updateInfoRequestBuild();
        Profile profile = Profile.createProfile("test", "test", 0L);

        Member updateMember = Member.updateInfo(request, member, profile);

        given(memberRepository.save(updateMember)).willReturn(updateMember);
        given(memberRepository.findById(updateMember.getId())).willReturn(Optional.of(updateMember));

        memberService.updateMember(request, member, profile);
        Member findMember = memberService.getMember(updateMember.getId());

        assertEquals("홍길동", findMember.getName());
        assertEquals(false, findMember.getIsMale());
        assertEquals("qwer@example.com", findMember.getEmail());
        assertEquals("010-9876-5432", findMember.getPhoneNumber());
        assertEquals("55555555", findMember.getStudentId());
        assertEquals(Department.AI, findMember.getDepartment());
    }

    @DisplayName("멤버 비밀번호를 변경하는 경우 입력한 비밀번호가 현재 사용중인 비밀번호와 일치하지 않을 때")
    @Test
    void 비밀번호_변경_실패() {
        UpdatePasswordRequest request = updatePasswordRequestBuildX();

        assertThrows(IllegalStateException.class, () ->
                memberService.updatePassword(request, member));
    }

    @DisplayName("멤버 비밀번호를 정상적으로 입력한 경우")
    @Test
    void 비밀번호_변경_성공() {
        UpdatePasswordRequest request = updatePasswordRequestBuildO();

        doReturn(Member.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(new Password(request.getPasswordAfter()))
                .build())
        .when(memberRepository).save(any());

        assertDoesNotThrow(() -> memberService.updatePassword(request, member));
    }

    private UpdateInfoRequest updateInfoRequestBuild() {
        UpdateInfoRequest request = UpdateInfoRequest.builder()
                .name("홍길동")
                .isMale(false)
                .email("qwer@example.com")
                .phoneNumber("010-9876-5432")
                .studentId("55555555")
                .department(Department.AI)
                .build();

        return request;
    }

    private UpdatePasswordRequest updatePasswordRequestBuildO() {
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                .passwordBefore("cuha")
                .passwordAfter("qwer1234")
                .build();

        return request;
    }

    private UpdatePasswordRequest updatePasswordRequestBuildX() {
        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                .passwordBefore("qwer1234")
                .passwordAfter("qwer1234")
                .build();

        return request;
    }
}