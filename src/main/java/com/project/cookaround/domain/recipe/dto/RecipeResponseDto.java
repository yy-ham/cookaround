package com.project.cookaround.domain.recipe.dto;

import com.project.cookaround.domain.recipe.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter @Setter
public class RecipeResponseDto {

    private Long id; // 레시피 번호
    private String category; // 카테고리
    private String title; // 제목
    private String createdAt; // 작성일자


    // Entity -> Dto 변환
    public static RecipeResponseDto fromEntity(Recipe recipe) {
        RecipeResponseDto dto = new RecipeResponseDto();
        dto.setId(recipe.getId());
        dto.setCategory(recipe.getCategory().getDescription());
        dto.setTitle(recipe.getTitle());
        dto.setCreatedAt(recipe.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //
        return dto;
    }

}
