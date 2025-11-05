package com.project.cookaround.domain.member.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.cookingtip.service.CookingTipService;
import com.project.cookaround.domain.member.dto.MemberRequestDto;
import com.project.cookaround.domain.member.dto.MemberResponseDto;
import com.project.cookaround.domain.member.dto.MessageResponseDto;
import com.project.cookaround.domain.member.entity.EmailVerificationResult;
import com.project.cookaround.domain.member.service.EmailVerificationService;
import com.project.cookaround.domain.member.service.MemberService;
import com.project.cookaround.domain.recipe.service.RecipeService;
import com.project.cookaround.domain.review.service.ReviewService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;
    private final RecipeService recipeService;
    private final CookingTipService cookingTipService;
    private final ReviewService reviewService;

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
    public boolean sendEmailCode(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "type") String type, HttpSession session) {

        // 회원가입 -> 가입된 이메일이 없어야 인증코드 발송
        // 아이디 찾기, 비밀번호 찾기 -> 가입된 이메일이 있어야 인증코드 발송

        if ("join".equals(type)) {
            // 회원가입
            if (!memberService.validateDuplicateMemberByEmail(email)) {
                saveVerificationInfoToSession(email, session);
            }
        } else {
            // 아이디 찾기, 비밀번호 찾기
            if (memberService.validateDuplicateMemberByEmail(email)) {
                saveVerificationInfoToSession(email, session);
            }
        }

        return false;
    }

    private boolean saveVerificationInfoToSession(String email, HttpSession session) {
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
    public String findId(Model model, String email, HttpSession session) {
        MemberResponseDto responseDto = MemberResponseDto.fromEntity(memberService.findLoginId(email));
        model.addAttribute("responseDto", responseDto);

        session.setAttribute("verifiedLoginId", responseDto.getLoginId());
        session.setAttribute("verifiedEmail", responseDto.getEmail());

        return "members/find-id-result";
    }


    // 비밀번호 찾기
    @GetMapping("find-password")
    public String findPasswordForm(Model model) {
        model.addAttribute("resetPasswordForm", new MemberRequestDto());
        return "members/find-password";
    }

    @PostMapping("/find-password")
    public String findPassword(Model model, MemberRequestDto resetPasswordForm, HttpSession session) {
        session.setAttribute("verifiedLoginId", resetPasswordForm.getLoginId());
        session.setAttribute("verifiedEmail", resetPasswordForm.getEmail());

        model.addAttribute("resetPasswordForm", resetPasswordForm);
        return "members/reset-password";
    }

    // 아이디 찾기 완료 후 이메일 인증 없이 비밀번호 재설정 폼으로 바로 이동
    @GetMapping("reset-password")
    public String resetPasswordForm(Model model, HttpSession session) {
        String loginId = (String) session.getAttribute("verifiedLoginId");

        MemberRequestDto resetPasswordForm = new MemberRequestDto();
        resetPasswordForm.setLoginId(loginId);

        model.addAttribute("resetPasswordForm", resetPasswordForm);

        return "members/reset-password";
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public String resetPassword(MemberRequestDto resetPasswordForm, HttpSession session, Model model) {
        String encodedPassword = passwordEncoder.encode(resetPasswordForm.getPassword());

        resetPasswordForm.setLoginId((String) session.getAttribute("verifiedLoginId"));
        resetPasswordForm.setEmail((String) session.getAttribute("verifiedEmail"));
        resetPasswordForm.setPassword(encodedPassword);

        memberService.resetPassword(resetPasswordForm.toEntity());

        session.removeAttribute("verifiedLoginId");
        session.removeAttribute("verifiedEmail");

        MessageResponseDto message = new MessageResponseDto();
        message.setMessage("비빌번호 재설정이 완료되었습니다. 다시 로그인 해주세요.");
        message.setRedirectUrl("/members/login");

        model.addAttribute("message", message);

        return "message-response";
    }

    // 마이페이지
    @GetMapping({"/mypage", "/mypage/posts"})
    public String mypage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 로그인 아이디 표시
        model.addAttribute("loginId", userDetails.getUsername());

        // 내가 쓴 글/후기 요약 박스
        Long memberId = userDetails.getId();
        model.addAttribute("recipeCount", recipeService.getRecipeCountByMemberId(memberId));
        model.addAttribute("cookingTipCount", cookingTipService.getCookingTipCountByMemberId(memberId));
        model.addAttribute("reviewCount", reviewService.getReviewCountByMemberId(memberId));
        model.addAttribute("reviewAverage", reviewService.getAverageRatingByMemberId(memberId));

        return "members/mypage";
    }

    // 프로필 이미지 변경
    @PostMapping("/mypage/profile")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails, MultipartFile newProfile,
                                @RequestParam(defaultValue="false") boolean resetDefault) {
        memberService.updateProfile(userDetails.getId(), newProfile, resetDefault);
        return "redirect:/members/mypage";
    }

}
