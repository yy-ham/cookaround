package com.project.cookaround.domain.member.controller;

import com.project.cookaround.domain.member.dto.MemberRequestDto;
import com.project.cookaround.domain.member.entity.EmailVerificationResult;
import com.project.cookaround.domain.member.service.EmailVerificationService;
import com.project.cookaround.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerificationService emailVerificationService;
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

    @ResponseBody
    @GetMapping("/check-login-id")
    public boolean checkDuplicateLoginId(@RequestParam(name = "loginId") String loginId) {
        return memberService.validateDuplicateMemberByLoginId(loginId);
    }

    @ResponseBody
    @GetMapping("/check-email")
    public boolean checkDuplicateEmail(@RequestParam(name = "email") String email) {
        return memberService.validateDuplicateMemberByEmail(email);
    }

    @ResponseBody
    @PostMapping("/send-email-code")
    public boolean sendEmailCode(@RequestParam(name = "email") String email, HttpSession session) {
        String verificationCode = emailVerificationService.sendVerificationEmail(email);
        if (verificationCode == null) {
            return false;
        } else {
            session.setAttribute("email",email);
            session.setAttribute("verificationCode", verificationCode);
            session.setAttribute("issuedAt", LocalDateTime.now());
            session.setMaxInactiveInterval(60 * 5);
            return true;
        }
    }

    @ResponseBody
    @PostMapping("/verify-email-code")
    public EmailVerificationResult verifyEmailCode(@RequestParam(name = "email") String email,
                                                   @RequestParam(name = "verificationCode") String verificationCode, HttpSession session) {
        return emailVerificationService.verifyVerificationCode(email, verificationCode, session);
    }


    // 로그인
    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        if (session.getAttribute("errorMessage") != null) {
            String loginId = session.getAttribute("loginId").toString();
            String errorMessage = session.getAttribute("errorMessage").toString();

            model.addAttribute("loginId", loginId);
            model.addAttribute("errorMessage", errorMessage);

            session.removeAttribute("loginId");
            session.removeAttribute("errorMessage");
        }
        return "member/login";
    }

}
