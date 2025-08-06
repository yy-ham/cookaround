package com.project.cookaround.domain.cookingtip.dto;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import com.project.cookaround.domain.image.dto.ImageResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CookingTipDetailResponseDto {

    private Long id;
    private Long memberId;
    private String loginId;
    private CookingTipCategory category;
    private String title;
    private String description;
    private String content;
    private List<ImageResponseDto> images;


    // Entity -> Dto 변환
    public static CookingTipDetailResponseDto fromEntity(CookingTip cookingTip, List<ImageResponseDto> images) {
        CookingTipDetailResponseDto responseDto = new CookingTipDetailResponseDto();
        responseDto.setId(cookingTip.getId());
        responseDto.setMemberId(cookingTip.getMember().getId());
        responseDto.setLoginId(cookingTip.getMember().getLoginId());
        responseDto.setCategory(cookingTip.getCategory());
        responseDto.setTitle(cookingTip.getTitle());
        responseDto.setDescription(cookingTip.getDescription());
        responseDto.setContent(cookingTip.getContent());
        responseDto.setImages(images);
        return responseDto;
    }

}
