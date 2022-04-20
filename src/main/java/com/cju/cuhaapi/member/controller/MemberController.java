package com.cju.cuhaapi.member.controller;

import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Profile;
import com.cju.cuhaapi.member.dto.*;
import com.cju.cuhaapi.member.service.MemberService;
import com.cju.cuhaapi.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ProfileService profileService;

    /**
     * 멤버 조회 sort(score)
     */
    @GetMapping
    public List<MemberResponse> members(Pageable pageable) {
        return memberService.findMembers(pageable);
    }

    /**
     * 단건 멤버 조회
     */
    @GetMapping("/info/{username}")
    public MemberResponse info(@PathVariable String username) {
        return memberService.findMember(username);
    }

    /**
     * 현재 로그인한 계정 조회
     */
    @GetMapping("/info")
    public MemberInfoResponse myInfo(@LoginMember Member authMember) {
        return memberService.myInfo(authMember.getId());
    }

    /**
     * 내 랭킹 조회
     */
    @GetMapping("/rank")
    public MemberRankInfoResponse myRank(@LoginMember Member loginMember) {
        return memberService.myRank(loginMember.getId());
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public MemberJoinResponse join(@RequestBody MemberJoinRequest request) {
        return memberService.saveMember(request);
    }

    /**
     * 멤버 정보 변경
     */
    @PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public void updateMember(@LoginMember Member loginMember,
                             @RequestPart("json") MemberUpdateInfoRequest request,
                             @RequestPart(required = false) MultipartFile profileFile) throws IOException {
        // 프로필 업로드
        Profile profile = profileService.saveProfileFile(profileFile);

        memberService.updateMember(loginMember, request, profile);
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/password")
    public void updatePassword(@LoginMember Member loginMember,
                               @RequestBody MemberUpdatePasswordRequest request) {
        memberService.updatePassword(loginMember, request);
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping
    public void delete(@LoginMember Member loginMember) {
        // 회원탈퇴
        memberService.deleteMember(loginMember);
    }
}
