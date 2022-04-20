package com.cju.cuhaapi.member.controller;

import com.cju.cuhaapi.commons.annotation.LoginMember;
import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.member.domain.entity.Profile;
import com.cju.cuhaapi.member.dto.*;
import com.cju.cuhaapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Value("${upload.path}")
    private String uploadPath;

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
        Profile profile = null;

        if (profileFile != null) {
            String originalFilename = profileFile.getOriginalFilename();
            Long size = profile.getSize();
            String ext = StringUtils.getFilenameExtension(originalFilename);
            String filename = createFilename(ext);

            saveFile(profileFile, filename);
            profile = Profile.createProfile(originalFilename, filename, size);
        }

        memberService.updateMember(loginMember.getId(), request, profile);
    }

    /**
     * 비밀번호 변경
     */
    @PatchMapping("/password")
    public void updatePassword(@LoginMember Member loginMember,
                               @RequestBody MemberUpdatePasswordRequest request) {
        memberService.updatePassword(loginMember.getId(), request);
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping
    public void delete(@LoginMember Member loginMember) {
        // 회원탈퇴
        memberService.deleteMember(loginMember.getId());
    }

    @GetMapping("/profiles/{filename}")
    public Resource downloadProfile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + getFullPath(filename));
    }

    //== 비지니스 메서드 ==//
    private String createFilename(String ext) {
        return UUID.randomUUID().toString() + "." + ext;
    }

    private String getFullPath(String filename) {
        return uploadPath + "/" + filename;
    }

    private void saveFile(MultipartFile multipartFile, String filename) throws IOException {
        File file = new File(getFullPath(filename));
        multipartFile.transferTo(file);
    }
}
