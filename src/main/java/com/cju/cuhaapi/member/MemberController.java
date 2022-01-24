package com.cju.cuhaapi.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     * id 값으로 멤버를 조회하는 메서드
     * @return Member
     */
    @GetMapping("/{id}")
    public Member memberInfo(@PathVariable Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return member;
    }

    /**
     *
     * @param joinReq
     * @return
     */
    @PostMapping
    public String join(@ModelAttribute MemberDto.JoinReq joinReq) {
        log.info("joinReq={}", joinReq);
        return "ok";
    }
}
