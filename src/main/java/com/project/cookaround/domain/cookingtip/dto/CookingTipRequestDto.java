package com.project.cookaround.domain.cookingtip.dto;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CookingTipRequestDto {

    private Long id;
    private CookingTipCategory category;
    private String title;
    private String description;
    private String content;


    // Dto -> Entity 변환
    public CookingTip toEntity() {
        CookingTip cookingTip = new CookingTip();
        cookingTip.setId(this.id);
        cookingTip.setCategory(this.category);
        cookingTip.setTitle(this.title);
        cookingTip.setDescription(this.description);
        cookingTip.setContent(this.content);
        return cookingTip;
    }

}
