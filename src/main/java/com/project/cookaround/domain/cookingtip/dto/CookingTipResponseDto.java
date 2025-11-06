package com.project.cookaround.domain.cookingtip.dto;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter @Setter
public class CookingTipResponseDto {

    private Long id;
    private String category;
    private String title;
    private String createdAt;

    // Entity -> Dto 변환
    public static CookingTipResponseDto fromEntity(CookingTip cookingTip) {
        CookingTipResponseDto dto = new CookingTipResponseDto();
        dto.setId(cookingTip.getId());
        dto.setCategory(cookingTip.getCategory().getDescription());
        dto.setTitle(cookingTip.getTitle());
        dto.setCreatedAt(cookingTip.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return dto;
    }

}
