package com.project.cookaround.domain.likes.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.likes.dto.LikesRequestDto;
import com.project.cookaround.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // 좋아요 등록
    @ResponseBody
    @PostMapping("/likes/new")
    public boolean registerLike(@RequestBody LikesRequestDto requestDto,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean isSuccess = false;
        if (userDetails != null) {
            likesService.registerLike(userDetails.getId(), requestDto.toEntity());
            isSuccess = true;
        }

        return isSuccess;
    }
}
