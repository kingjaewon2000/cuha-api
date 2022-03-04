package com.cju.cuhaapi.member;

import com.cju.cuhaapi.security.auth.PrincipalDetails;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import static com.cju.cuhaapi.member.MemberDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final ModelMapper modelMapper;
    private final MemberService memberService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 멤버 조회
     */
    @ApiOperation(value = "멤버 조회", notes = "현재 로그인중인 회원의 정보를 조회합니다.")
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
    @ApiOperation(value = "회원가입", notes = "회원을 저장합니다.")
    @PostMapping("/join")
    public InfoResponse join(@Validated @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        // 입력 값 검증
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("모든 필드를 채워주셔야 합니다.");
        }

        // 패스워드 검증
        if (isNotEqualPassword(joinRequest.getPassword(), joinRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 같지 않습니다.");
        }

        Member member = modelMapper.map(joinRequest, Member.class);
        Member savedMember = memberService.saveMember(member);

        InfoResponse infoResponse = modelMapper.map(savedMember, InfoResponse.class);

        return infoResponse;
    }

    /**
     * 멤버 정보 변경
     */
    @ApiOperation(value = "멤버 정보 변경", notes = "현재 로그인중인 회원의 정보를 변경합니다.")
    @PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public InfoResponse updateInfo(Authentication authentication,
                                   @RequestPart("json") UpdateInfoRequest updateInfoRequest,
                                   @RequestPart(required = false) MultipartFile profileFile) throws IOException {
        // 인증된 멤버
        PrincipalDetails principalDetails = ((PrincipalDetails)authentication.getPrincipal());
        Member authMember = principalDetails.getMember();

        // 프로필 업로드
        Profile profile = null;

        if (profileFile != null) {
            String originalFilename = profileFile.getOriginalFilename();
            String ext = StringUtils.getFilenameExtension(originalFilename);
            String filename = createFilename(ext);
            File file = new File(getFullPath(filename));
            profileFile.transferTo(file);

            profile = Profile.builder()
                    .originalFilename(profileFile.getOriginalFilename())
                    .newFilename(filename)
                    .size(profileFile.getSize())
                    .build();
        }

        // 멤버 정보 변경
        Member member = modelMapper.map(updateInfoRequest, Member.class);
        member.setId(authMember.getId());
        member.setProfile(profile);
        Member updatedMember = memberService.updateMember(member);

        InfoResponse infoResponse = modelMapper.map(updatedMember, InfoResponse.class);

        return infoResponse;
    }


    /**
     * 비밀번호 변경
     */
    @ApiOperation(value = "비밀번호 변경", notes = "현재 로그인중인 회원의 비밀번호를 변경합니다.")
    @PatchMapping("/password")
    public IdResponse updatePassword(Authentication authentication, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        // 패스워드 검증
        if (isNotEqualPassword(updatePasswordRequest.getPassword(), updatePasswordRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("패스워드와 패스워드 확인이 같지 않습니다.");
        }

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
    @ApiOperation(value = "회원탈퇴", notes = "현재 로그인중인 회원을 탈퇴합니다.")
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

    private boolean isNotEqualPassword(String password, String repeatPassword) {
        return !(password.equals(repeatPassword));
    }

}
