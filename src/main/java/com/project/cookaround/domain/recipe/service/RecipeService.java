package com.project.cookaround.domain.recipe.service;

import com.project.cookaround.domain.recipe.dto.RecipeDto;
import com.project.cookaround.domain.recipe.entity.Recipe;
import com.project.cookaround.domain.recipe.mapper.RecipeMapper;
import com.project.cookaround.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 필드만 골라서 생성자 자동 생성
@Service
public class RecipeService {

    private final RecipeMapper recipeMapper; // MyBatis
    private final RecipeRepository recipeRepository; // JPA

    public List<RecipeDto> showRecipeList() {
        return recipeMapper.showRecipeList();
    }

    // 레시피 목록 페이지(Ajax)
    public List<RecipeDto> showRecipeListJson(String category, String sort) {
        return recipeMapper.showRecipeListJson(category, sort);
    }

    // 레시피 상세 페이지
    public String showRecipeDetail(String recipeId) {
        return recipeMapper.showRecipeDetail(recipeId);
    }

    // 마이페이지 - 내가 쓴 글/후기, 레시피 개수 출력
    public Long getRecipeCountByMemberId(Long memberId) {
        Long cnt = recipeRepository.countByMemberId(memberId);
        if (cnt != 0) {
            return cnt;
        }
        return 0L;
    }

    // 마이페이지 - 내가 쓴 글/후기, 레시피 조회
    public List<Recipe> getRecipeByMemberId(Long MemberId) {
        return recipeRepository.findByMemberIdOrderById(MemberId);
    }

}
