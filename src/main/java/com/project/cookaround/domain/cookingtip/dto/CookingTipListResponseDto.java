package com.project.cookaround.domain.cookingtip.dto;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CookingTipListResponseDto {

    private Long id;
    private String title;
    private Long memberId;
    private String loginId;
    private String coverImage;


    // Entity -> Dto 변환
    public static CookingTipListResponseDto fromEntity(CookingTip cookingTip) {
        CookingTipListResponseDto responseDto = new CookingTipListResponseDto();
        responseDto.setId(cookingTip.getId());
        responseDto.setTitle(cookingTip.getTitle());
        responseDto.setMemberId(cookingTip.getMember().getId());
        responseDto.setLoginId(cookingTip.getMember().getLoginId());
        return responseDto;
    }

}
