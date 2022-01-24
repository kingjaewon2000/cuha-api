package com.cju.cuhaapi.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.cju.cuhaapi.member.MemberDto.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 멤버 조회
     */
    @GetMapping("/{id}")
    public Member memberInfo(@PathVariable Long id) {
        Member member = memberService.getMember(id);
        return member;
    }

    /**
     * 회원가입
     */
    @PostMapping
    public String join(@RequestBody JoinReq joinReq) {
        memberService.saveMember(joinReq);
        return "ok";
    }
}
