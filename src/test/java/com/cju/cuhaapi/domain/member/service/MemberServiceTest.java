package com.cju.cuhaapi.domain.member.service;

import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdateInfoRequest;
import com.cju.cuhaapi.domain.member.dto.MemberDto.UpdatePasswordRequest;
import com.cju.cuhaapi.domain.member.entity.Department;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.repository.MemberRepository;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.cju.cuhaapi.mapper.MemberMapper.INSTANCE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @SpringBootTest를 사용하여 매우 느림.
 * 개선 여지가 있음
 */
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private Member savedMember;

    @BeforeEach
    void beforeEach() {
        memberRepository.deleteAll();

        Member member = createMember();
        this.savedMember = memberRepository.save(member);
    }

    private Member createMember() {
        Member member = Member.builder()
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

    @DisplayName("멤버 조회 테스트")
    @Test
    void 멤버_조회() {
        Member findMember = memberService.findMember(savedMember.getId());

        assertEquals(savedMember.getId(), findMember.getId());
        assertEquals(savedMember.getUsername(), findMember.getUsername());
    }

    @DisplayName("멤버 저장 테스트")
    @Test
    void 멤버_저장() {
        Member member = Member.builder()
                .username("test")
                .password(new Password("test"))
                .name("홍길동")
                .isMale(true)
                .email("cuha@cju.ac.klr")
                .phoneNumber("010-1234-5678")
                .studentId("00000000")
                .department(Department.DIGITAL_SECURITY)
                .build();


        assertDoesNotThrow(() -> memberService.saveMember(member));
    }

    @DisplayName("멤버 정보 수정 테스트(프로필 X)")
    @Test
    void 멤버_정보_수정() {
        UpdateInfoRequest request = UpdateInfoRequest.builder()
                .name("홍길동")
                .isMale(false)
                .email("qqqq@example.co.kr")
                .phoneNumber("010-9999-9999")
                .studentId("99999999")
                .department("AI")
                .build();
        Member member = INSTANCE.updateInfoRequestToEntity(request, savedMember, savedMember.getProfile());

        memberService.updateMember(member);
        Member findMember = memberService.findMember(savedMember.getId());

        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getName(), findMember.getName());
        assertEquals(member.getIsMale(), findMember.getIsMale());
        assertEquals(member.getEmail(), findMember.getEmail());
        assertEquals(member.getPhoneNumber(), findMember.getPhoneNumber());
        assertEquals(member.getStudentId(), findMember.getStudentId());
        assertEquals(member.getDepartment(), findMember.getDepartment());
    }

    @DisplayName("멤버 비밀번호 수정 테스트")
    @Test
    void 멤버_비밀번호_수정() {
        String passwordBefore = "cuha";
        String passwordAfter = "qwer1234";

        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                .passwordBefore(passwordBefore)
                .passwordAfter(passwordAfter)
                .build();

        Member member = INSTANCE.updatePasswordRequestToEntity(request, savedMember);

        memberService.updatePassword(member);
        Member findMember = memberService.findMember(savedMember.getId());

        assertEquals(member.getId(), findMember.getId());
        assertEquals(member.getUsername(), findMember.getUsername());
        boolean isEqualPassword = PasswordEncoderUtils.getInstance().
                matchers(passwordAfter,
                        findMember.getPassword().getValue());
        assertEquals(true, isEqualPassword);
    }

    @DisplayName("멤버 탈퇴 테스트")
    @Test
    void 멤버_탈퇴() {
        long countBefore = memberRepository.count();

        memberService.deleteMember(savedMember);

        long countAfter = memberRepository.count();
        assertEquals(countBefore - 1, countAfter);
    }
}