package com.cju.cuhaapi.controller;

import com.cju.cuhaapi.annotation.CurrentMember;
import com.cju.cuhaapi.controller.dto.MemberDto.*;
import com.cju.cuhaapi.entity.member.Member;
import com.cju.cuhaapi.entity.member.Profile;
import com.cju.cuhaapi.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 멤버 조회
     */
    @ApiOperation(value = "범위 멤버 조회", notes = "범위 회원의 정보를 조회합니다.")
    @GetMapping
    public List<MemberResponse> members(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "100") Integer size) {
        return memberService.getMembers(page, size).stream()
                .map(member -> MemberResponse.of(member))
                .collect(Collectors.toList());
    }

    /**
     * 멤버 조회(점수 기반)
     */
    @ApiOperation(value = "범위 랭킹 조회", notes = "범위 회원의 랭킹 정보를 순서로 조회합니다.")
    @GetMapping("/ranking")
    public List<MemberResponse> membersByScore(@RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "100") Integer size) {
        return memberService.getMembersOrderByScore(page, size).stream()
                .map(member -> MemberResponse.of(member))
                .collect(Collectors.toList());
    }

    /**
     * 내 랭킹 출력
     */
    @ApiOperation(value = "내 랭킹 조회", notes = "내 랭킹을 조회합니다.")
    @GetMapping("/ranking/me")
    public RankingResponse rankingMe(@CurrentMember Member authMember) {
        Long authMemberId = authMember.getId();
        Long ranking = memberService.ranking(authMemberId);

        return RankingResponse.of(ranking, authMember.getUsername());
    }

    /**
     * 로그인한 멤버 조회
     */
    @ApiOperation(value = "로그인한 멤버 조회", notes = "현재 로그인중인 회원의 정보를 조회합니다.")
    @GetMapping("/me")
    public MemberResponse info(@CurrentMember Member authMember) {
        return MemberResponse.of(authMember);
    }

    /**
     * 회원가입
     */
    @ApiOperation(value = "회원가입", notes = "회원을 저장합니다.")
    @PostMapping("/join")
    public void join(@RequestBody JoinRequest request) {
        memberService.saveMember(request);
    }

    /**
     * 멤버 정보 변경
     */
//    @ApiOperation(value = "정보 변경", notes = "현재 로그인중인 회원의 정보를 변경합니다.")
    @PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public void updateMember(@CurrentMember Member authMember,
                             @RequestPart("json") UpdateMemberRequest request,
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

        memberService.updateMember(request, authMember, profile);
    }

    /**
     * 비밀번호 변경
     */
    @ApiOperation(value = "비밀번호 변경", notes = "현재 로그인중인 회원의 비밀번호를 변경합니다.")
    @PatchMapping("/password")
    public void updatePassword(@CurrentMember Member authMember,
                               @RequestBody UpdatePasswordRequest request) {
        memberService.updatePassword(request, authMember);
    }

    /**
     * 회원탈퇴
     */
    @ApiOperation(value = "회원탈퇴", notes = "현재 로그인중인 회원을 탈퇴합니다.")
    @DeleteMapping
    public void delete(@CurrentMember Member authMember) {
        // 회원탈퇴
        memberService.deleteMember(authMember);
    }

    @ApiOperation(value = "프로필", notes = "파일 이름에 해당하는 프로필을 가져옵니다.")
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