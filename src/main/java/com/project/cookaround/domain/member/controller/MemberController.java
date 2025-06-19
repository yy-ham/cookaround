package com.project.cookaround.domain.member.controller;

import com.project.cookaround.domain.member.dto.MemberRequestDto;
import com.project.cookaround.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberRequestDto", new MemberRequestDto());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(MemberRequestDto memberRequestDto) {
        memberRequestDto.setPassword(passwordEncoder.encode(memberRequestDto.getPassword()));
        memberService.join(memberRequestDto.toEntity());
        return "redirect:/";
    }

}
