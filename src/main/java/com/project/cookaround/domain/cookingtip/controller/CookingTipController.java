package com.project.cookaround.domain.cookingtip.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.cookingtip.dto.CookingTipRequestDto;
import com.project.cookaround.domain.cookingtip.service.CookingTipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CookingTipController {

    private final CookingTipService cookingTipService;

    // 요리팁 전체 목록
    @GetMapping("/cooking-tips")
    public String list() {
        return "cooking-tips/list";
    }

    // 요리팁 상세 조회
    @GetMapping("/cooking-tips/{id}")
    public String detail() {
        return "cooking-tips/detail";
    }

    // 요리팁 등록
    @GetMapping("/cooking-tips/new")
    public String createForm(Model model) {
        model.addAttribute("cookingTipForm", new CookingTipRequestDto());
        return "cooking-tips/new";
    }

    @PostMapping("/cooking-tips/new")
    public String createCookingTip(CookingTipRequestDto cookingTipForm, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long cookingTipId = cookingTipService.registerCookingTip(userDetails.getId(), cookingTipForm.toEntity());
        return "redirect:/cooking-tips/" + cookingTipId;
    }

}