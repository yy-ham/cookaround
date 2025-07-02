package com.project.cookaround.domain.member.controller;

import com.project.cookaround.domain.member.dto.MemberRequestDto;
import com.project.cookaround.domain.member.dto.MemberResponseDto;
import com.project.cookaround.domain.member.entity.EmailVerificationResult;
import com.project.cookaround.domain.member.service.EmailVerificationService;
import com.project.cookaround.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberRequestDto", new MemberRequestDto());
        return "members/join";
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
            session.setAttribute("email", email);
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
    public String loginForm(Model model, HttpSession session, HttpServletRequest request) {
        String loginId = null;
        boolean isSaved = false;

        // 로그인 실패
        if (session.getAttribute("errorMessage") != null) {
            loginId = session.getAttribute("loginId").toString();
            String errorMessage = session.getAttribute("errorMessage").toString();

            model.addAttribute("errorMessage", errorMessage);

            session.removeAttribute("loginId");
            session.removeAttribute("errorMessage");

            if (session.getAttribute("isSaved") != null) {
                isSaved = true;
            }
        }

        // 아이디 저장
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedId".equals(cookie.getName())) {
                    if (loginId == null) {
                        loginId = cookie.getValue();
                        isSaved = true;
                    }
                    break;
                }
            }
        }

        model.addAttribute("loginId", loginId);
        model.addAttribute("isSaved", isSaved);

        return "members/login";
    }


    // 아이디 찾기
    @GetMapping("/find-id")
    public String findIdForm() {
        return "members/find-id";
    }

    @PostMapping("/find-id")
    public String findId(Model model, String email) {
        MemberResponseDto responseDto = MemberResponseDto.fromEntity(memberService.findLoginId(email));
        model.addAttribute("responseDto", responseDto);
        return "members/find-id-result";
    }

}
