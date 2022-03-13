package com.cju.cuhaapi.domain.member.controller;

import com.cju.cuhaapi.domain.member.dto.MemberDto;
import com.cju.cuhaapi.domain.member.dto.MemberDto.*;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.member.entity.Password;
import com.cju.cuhaapi.domain.member.entity.Profile;
import com.cju.cuhaapi.domain.member.service.MemberService;
import com.cju.cuhaapi.security.auth.PrincipalDetails;
import com.cju.cuhaapi.utils.PasswordEncoderUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import static com.cju.cuhaapi.mapper.MemberMapper.INSTANCE;

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
    @ApiOperation(value = "멤버 조회", notes = "현재 로그인중인 회원의 정보를 조회합니다.")
    @GetMapping
    public InfoResponse info(Authentication authentication) {
        // Authentication 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 응답
        InfoResponse response = INSTANCE.entityToInfoResponse(authMember);

        return response;
    }

    /**
     * 회원가입
     */
    @ApiOperation(value = "회원가입", notes = "회원을 저장합니다.")
    @PostMapping("/join")
    public InfoResponse join(@RequestBody JoinRequest joinRequest) {
        Member member = INSTANCE.joinRequestToEntity(joinRequest);
        memberService.saveMember(member);
        InfoResponse response = INSTANCE.entityToInfoResponse(member);

        return response;
    }

    /**
     * 멤버 정보 변경
     */
    @ApiOperation(value = "멤버 정보 변경", notes = "현재 로그인중인 회원의 정보를 변경합니다.")
    @PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public InfoResponse updateInfo(Authentication authentication,
                                   @RequestPart("json") UpdateInfoRequest updateInfoRequest,
                                   @RequestPart(required = false) MultipartFile profileFile) throws IOException {
        // Authentication 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 프로필 업로드
        Profile profile = authMember.getProfile();

        if (profileFile != null) {
            String originalFilename = profileFile.getOriginalFilename();
            Long size = profile.getSize();
            String ext = StringUtils.getFilenameExtension(originalFilename);
            String filename = createFilename(ext);

            saveFile(profileFile, filename);
            profile = multipartFileToEntity(originalFilename, filename, size);
        }

        Member member = INSTANCE.updateInfoRequestToEntity(updateInfoRequest, authMember, profile);
        memberService.updateMember(member);
        InfoResponse response = INSTANCE.entityToInfoResponse(member);

        return response;
    }

    private Profile multipartFileToEntity(String originalFilename, String filename, Long size) {
        Profile profile = Profile.builder()
                .originalFilename(originalFilename)
                .filename(filename)
                .size(size)
                .build();

        return profile;
    }

    private void saveFile(MultipartFile multipartFile, String filename) throws IOException {
        File file = new File(getFullPath(filename));
        multipartFile.transferTo(file);
    }

    /**
     * 비밀번호 변경
     */
    @ApiOperation(value = "비밀번호 변경", notes = "현재 로그인중인 회원의 비밀번호를 변경합니다.")
    @PatchMapping("/password")
    public InfoResponse updatePassword(Authentication authentication,
                                       @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        // Authentication 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        String passwordBefore = updatePasswordRequest.getPasswordBefore();
        String passwordAfter = updatePasswordRequest.getPasswordAfter();

        // 비밀번호 변경
        Member member = memberService.updatePassword(authMember.getId(), passwordBefore, passwordAfter);
        InfoResponse response = INSTANCE.entityToInfoResponse(member);

        return response;
    }

    /**
     * 회원탈퇴
     */
    @ApiOperation(value = "회원탈퇴", notes = "현재 로그인중인 회원을 탈퇴합니다.")
    @DeleteMapping
    public DeleteResponse delete(Authentication authentication) {
        // Authentication 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 회원탈퇴
        memberService.deleteMember(authMember);

        // 응답 객체 생성
        DeleteResponse response = new DeleteResponse(authMember.getId());

        return response;
    }

    @ApiOperation(value = "프로필", notes = "파일 이름에 해당하는 프로필을 가져옵니다.")
    @GetMapping("/profiles/{filename}")
    public Resource downloadProfile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + getFullPath(filename));
    }

    private String createFilename(String ext) {
        return UUID.randomUUID().toString() + "." + ext;
    }

    private String getFullPath(String filename) {
        return uploadPath + "/" + filename;
    }
}
