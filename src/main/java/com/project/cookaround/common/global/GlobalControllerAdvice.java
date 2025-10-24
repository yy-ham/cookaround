package com.project.cookaround.common.global;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.member.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final MemberService memberService;

    public GlobalControllerAdvice(MemberService memberService) {
        this.memberService = memberService;
    }

    // 헤더에 로그인 사용자 프로필 이미지 표시
    @ModelAttribute("profile")
    public String addProfileToModel(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            return memberService.getProfile(userDetails.getId());
        }
        return null;
    }
}
