package com.project.cookaround.domain.cookingtip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CookingTipController {

    // 요리팁 전체 목록
    @GetMapping("/cooking-tips")
    public String list() {
        return "cooking-tips/list";
    }

}