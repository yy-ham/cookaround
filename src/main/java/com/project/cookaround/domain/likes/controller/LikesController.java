package com.project.cookaround.domain.likes.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.likes.dto.LikesRequestDto;
import com.project.cookaround.domain.likes.dto.LikesResponseDto;
import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    // 좋아요 등록
    @ResponseBody
    @PostMapping("/likes/new")
    public Map<String, Object> registerLike(@RequestBody LikesRequestDto requestDto,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        boolean isSuccess = false;
        Long likeCount = null;
        Long likeId = null;

        if (userDetails != null) {
            Map<String, Long> savedLike = likesService.registerLike(userDetails.getId(), requestDto.toEntity());
            likeId = savedLike.get("likeId");
            likeCount = savedLike.get("likeCount");
            isSuccess = true;
        }

        response.put("isSuccess", isSuccess);
        response.put("likeId", likeId);
        response.put("likeCount", likeCount);

        return response;
    }

    // 좋아요 삭제
    @ResponseBody
    @DeleteMapping("/likes/{id}")
    public Map<String, Object> deleteLike(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        boolean isSuccess = false;
        Long likeCount = null;

        if (userDetails != null) {
            likeCount = likesService.deleteLike(id);
            isSuccess = true;
        }

        response.put("isSuccess", isSuccess);
        response.put("likeCount", likeCount);

        return response;
    }

    // 좋아요 여부 조회
    @ResponseBody
    @GetMapping("/cooking-tips/{id}/likes")
    public LikesResponseDto getLikeById(@PathVariable Long id, @RequestParam LikesContentType contentType,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean isLiked = false;
        Likes like = null;
        LikesResponseDto responseDto = null;

        if (userDetails != null) {
            isLiked = likesService.existsLikeByMemberIdAndContentTypeAndContentId(userDetails.getId(), contentType, id);
        }

        if (isLiked) {
            like = likesService.getLikeByMemberIdAndContentTypeAndContentId(userDetails.getId(), contentType, id);
            responseDto = LikesResponseDto.fromEntity(like);
        }

        return responseDto;
    }
}
