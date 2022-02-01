package com.cju.cuhaapi.member;

import com.cju.cuhaapi.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cju.cuhaapi.member.MemberDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final ModelMapper modelMapper;
    private final MemberService memberService;

    /**
     * 멤버 조회
     */
    @GetMapping
    public InfoResponse info(Authentication authentication) {
        // 인증된 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 멤버 조회
        Member member = memberService.getMember(authMember.getId());

        InfoResponse infoResponse = modelMapper.map(member, InfoResponse.class);

        return infoResponse;
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public InfoResponse join(@Validated @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("모든 필드를 채워주셔야 합니다.");
        }

        Member member = modelMapper.map(joinRequest, Member.class);
        Member savedMember = memberService.saveMember(member);

        InfoResponse infoResponse = modelMapper.map(savedMember, InfoResponse.class);

        return infoResponse;
    }

    /**
     * 멤버 정보 변경
     */
    @PatchMapping
    public InfoResponse updateInfo(Authentication authentication, @RequestBody UpdateInfoRequest updateInfoRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 멤버 정보 변경
        Member member = modelMapper.map(updateInfoRequest, Member.class);
        member.setId(authMember.getId());
        Member updatedMember = memberService.updateMember(member);

        InfoResponse infoResponse = modelMapper.map(updatedMember, InfoResponse.class);

        return infoResponse;
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/password")
    public IdResponse updatePassword(Authentication authentication, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        // 인증된 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 비밀번호 변경
        Member member = modelMapper.map(updatePasswordRequest, Member.class);
        member.setId(authMember.getId());
        memberService.updatePassword(member);

        // 응답 객체 생성
        IdResponse response = modelMapper.map(member, IdResponse.class);

        return response;
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping
    public IdResponse delete(Authentication authentication) {
        // 인증된 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 회원탈퇴
        Member member = new Member();
        member.setId(authMember.getId());
        memberService.deleteMember(member);

        // 응답 객체 생성
        IdResponse response = modelMapper.map(member, IdResponse.class);

        return response;
    }
}
