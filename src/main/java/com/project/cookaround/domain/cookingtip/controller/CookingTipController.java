package com.project.cookaround.domain.cookingtip.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.cookingtip.dto.CookingTipDetailResponseDto;
import com.project.cookaround.domain.cookingtip.dto.CookingTipRequestDto;
import com.project.cookaround.domain.cookingtip.dto.CookingTipListResponseDto;
import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.service.CookingTipService;
import com.project.cookaround.domain.image.dto.ImageResponseDto;
import com.project.cookaround.domain.image.entity.Image;
import com.project.cookaround.domain.image.entity.ImageContentType;
import com.project.cookaround.domain.image.service.ImageService;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import com.project.cookaround.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CookingTipController {

    private final CookingTipService cookingTipService;
    private final ImageService imageService;
    private final LikesService likesService;

    private static final ImageContentType CONTENT_TYPE = ImageContentType.COOKINGTIP;

    // 요리팁 전체 목록
    @GetMapping("/cooking-tips")
    public String list(Model model, @RequestParam(defaultValue = "LATEST") String sort) {
        List<CookingTip> cookingTips = cookingTipService.getAllCookingTips(sort);
        if (!cookingTips.isEmpty()) {
            List<CookingTipListResponseDto> cookingTipResponseDtos = cookingTips.stream()
                    .map(cookingTip -> {
                        Image image = imageService.getFirstImageByContentTypeAndContentId(ImageContentType.COOKINGTIP, cookingTip.getId());
                        ImageResponseDto imageResponseDto = ImageResponseDto.fromEntity(image);
                        CookingTipListResponseDto cookingTipListResponseDto = CookingTipListResponseDto.fromEntity(cookingTip);
                        cookingTipListResponseDto.setCoverImage(imageResponseDto.getFileName());
                        return cookingTipListResponseDto;
                    }).toList();
            model.addAttribute("cookingTips", cookingTipResponseDtos);
        }

        return "cooking-tips/list";
    }

    // 요리팁 상세 조회
    @GetMapping("/cooking-tips/{id}")
    public String detail(Model model, @PathVariable(name = "id") Long cookingTipId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CookingTip cookingTip = cookingTipService.getCookingTipDetail(cookingTipId);
        boolean isLiked = false;
        boolean isOwner = false;

        List<Image> images = imageService.getImagesByContentTypeAndContentId(CONTENT_TYPE, cookingTipId);
        List<ImageResponseDto> imageResponseDtos = images.stream()
                .map(image -> {
                    ImageResponseDto responseDto = ImageResponseDto.fromEntity(image);
                    return responseDto;
                }).toList();

        CookingTipDetailResponseDto cookingTipDetailResponseDto = CookingTipDetailResponseDto.fromEntity(cookingTip, imageResponseDtos);

        if (userDetails != null) {
            isLiked = likesService.existsLikeByMemberIdAndContentTypeAndContentId(userDetails.getId(), LikesContentType.COOKINGTIP, cookingTipId);

            if (userDetails.getId().equals(cookingTip.getMember().getId())) {
                isOwner = true;
            }
        }

        model.addAttribute("cookingTip", cookingTipDetailResponseDto);
        model.addAttribute("isLiked", isLiked);
        model.addAttribute("isOwner", isOwner);

        return "cooking-tips/detail";
    }

    // 요리팁 등록
    @GetMapping("/cooking-tips/new")
    public String createForm(Model model) {
        model.addAttribute("cookingTipForm", new CookingTipRequestDto());
        return "cooking-tips/new";
    }

    @ResponseBody
    @PostMapping("/cooking-tips/new")
    public Long createCookingTip(CookingTipRequestDto cookingTipForm, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 요리팁 등록
        Long cookingTipId = cookingTipService.registerCookingTip(userDetails.getId(), cookingTipForm.toEntity());

        // 사진 등록
        imageService.registerImages(cookingTipForm.getImages(), ImageContentType.COOKINGTIP, cookingTipId);

        return cookingTipId;
    }

    // 요리팁 수정
    @GetMapping("/cooking-tips/{id}/edit")
    public String editForm(@PathVariable(name = "id") Long cookingTipId, Model model) {
        // 내용 가져오기
        CookingTip cookingTip = cookingTipService.getCookingTipDetail(cookingTipId);
        // 이미지 가져오기
        List<Image> images = imageService.getImagesByContentTypeAndContentIdOrderByIdAsc(CONTENT_TYPE, cookingTipId);

        // Entity -> Dto 변환
        // 이미지
        List<ImageResponseDto> imageResponseDtos = new ArrayList<>();
        for (Image image : images) {
            ImageResponseDto responseDto = ImageResponseDto.fromEntity(image);
            imageResponseDtos.add(responseDto);
        }
        // 요리팁
        CookingTipDetailResponseDto responseDto = CookingTipDetailResponseDto.fromEntity(cookingTip, imageResponseDtos);

        model.addAttribute("response", responseDto);

        return "cooking-tips/edit";
    }

    @ResponseBody
    @PatchMapping("/cooking-tips/{id}")
    public Long editCookingTip(@PathVariable(name = "id") Long cookingTipId,
                               CookingTipRequestDto cookingTipForm,
                               @RequestParam(required = false) List<Long> deletedImages,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 내용 수정
        Long modifiedId = cookingTipService.updateCookingTip(userDetails.getId(), cookingTipForm.toEntity());

        // 이미지 수정
        // 신규 이미지 등록
        if (cookingTipForm.getImages() != null) {
            imageService.registerImages(cookingTipForm.getImages(), CONTENT_TYPE, cookingTipId);
        }

        // 이미지 삭제
        if (deletedImages != null) {
            imageService.deleteImages(deletedImages, CONTENT_TYPE, cookingTipId);
        }

        return modifiedId;
    }

    // 요리팁 삭제
    @ResponseBody
    @DeleteMapping("/cooking-tips/{id}")
    public boolean deleteCookingTip(@PathVariable(name = "id") Long cookingTipId) {
        return cookingTipService.deleteCookingTip(cookingTipId);
    }

}