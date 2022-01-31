package com.cju.cuhaapi.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cju.cuhaapi.member.MemberDto.JoinRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    /**
     * 멤버 조회
     */
    @GetMapping("/{id}")
    public Member info(@PathVariable Long id) {
        Member member = memberService.getMember(id);

        return member;
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public Member join(@Validated @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("모든 필드를 채워주셔야 합니다.");
        }

        Member member = modelMapper.map(joinRequest, Member.class);
        Member savedMember = memberService.saveMember(member);

        return savedMember;
    }

    /**
     * 멤버 정보 변경
     */
    @PatchMapping
    public String updateInfo() {
        return "ok";
    }
}
