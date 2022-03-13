package com.cju.cuhaapi.domain.member.service;

import com.cju.cuhaapi.domain.member.dto.MemberDto;
import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.domain.member.entity.Department;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.entity.Profile;
import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.domain.member.repository.ProfileRepository;
import com.cju.cuhaapi.error.exception.DuplicateUsernameException;
import com.cju.cuhaapi.mapper.MemberMapper;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cju.cuhaapi.mapper.MemberMapper.INSTANCE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
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

    private Member memberBuild() {
        Member member = Member.builder()
                .id(1L)
                .username("cuha")
                .password(new Password("cuha"))
                .name("김태형")
                .isMale(true)
                .email("cuha@cju.ac.klr")
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
        given(memberRepository.save(member)).willReturn(member);
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        memberService.saveMember(member);
        Member findMember = memberService.findMember(1L);

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());
    }

    @DisplayName("회원가입 할때 아이디가 중복인 경우")
    @Test
    void 멤버_회원가입_중복O() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(true);

        assertThrows(DuplicateUsernameException.class,
                () -> memberService.saveMember(member));
    }

    @DisplayName("회원가입 할때 아이디가 중복이 아닌 경우")
    @Test
    void 멤버_회원가입_중복X() {
        given(memberRepository.existsByUsername(member.getUsername())).willReturn(false);

        assertDoesNotThrow(() -> memberService.saveMember(member));
    }

    @DisplayName("내 정보를 변경하는 경우(프로필 NULL)")
    @Test
    void 내_정보_변경_프로필_NULL() {
        UpdateInfoRequest request = updateInfoRequestBuild();
        Profile profile = null;

        Member updateMember = INSTANCE.updateInfoRequestToEntity(request, member, profile);

        assertThrows(IllegalArgumentException.class,
                () -> memberService.updateMember(updateMember));
    }

    @DisplayName("내 정보를 변경하는 경우(프로필 포함 O)")
    @Test
    void 내_정보_변경() {
        UpdateInfoRequest request = updateInfoRequestBuild();
        Profile profile = new Profile();

        Member updateMember = INSTANCE.updateInfoRequestToEntity(request, member, profile);

        given(memberRepository.save(updateMember)).willReturn(updateMember);
        given(memberRepository.findById(updateMember.getId())).willReturn(Optional.of(updateMember));

        memberService.updateMember(updateMember);
        Member findMember = memberService.findMember(updateMember.getId());

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

        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));

        assertThrows(IllegalStateException.class, () ->
                memberService.updatePassword(member.getId(),
                        request.getPasswordBefore(),
                        request.getPasswordAfter()));
    }

    @DisplayName("멤버 비밀번호를 정상적으로 입력한 경우")
    @Test
    void 비밀번호_변경_성공() {
        UpdatePasswordRequest request = updatePasswordRequestBuildO();

        given(memberRepository.findById(member.getId())).willReturn(Optional.ofNullable(member));
        doReturn(Member.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(new Password(request.getPasswordAfter()))
                .build())
        .when(memberRepository).save(any());

        Member updatedMember = memberService.updatePassword(member.getId(),
                request.getPasswordBefore(),
                request.getPasswordAfter());

        boolean isMatchPassword = PasswordEncoderUtils.getInstance()
                .matchers(request.getPasswordAfter(), updatedMember.getPassword().getValue());
        assertEquals(member.getId(), updatedMember.getId());
        assertEquals(member.getUsername(), updatedMember.getUsername());
        assertEquals(true, isMatchPassword);
    }

    private UpdateInfoRequest updateInfoRequestBuild() {
        UpdateInfoRequest request = UpdateInfoRequest.builder()
                .name("홍길동")
                .isMale(false)
                .email("qwer@example.com")
                .phoneNumber("010-9876-5432")
                .studentId("55555555")
                .department(Department.AI.name())
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