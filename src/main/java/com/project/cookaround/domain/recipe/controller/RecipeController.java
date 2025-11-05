package com.project.cookaround.domain.recipe.controller;

import com.project.cookaround.common.security.CustomUserDetails;
import com.project.cookaround.domain.recipe.dto.RecipeDto;
import com.project.cookaround.domain.recipe.dto.RecipeResponseDto;
import com.project.cookaround.domain.recipe.entity.Recipe;
import com.project.cookaround.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor // final이 붙은 필드만 골라서 생성자 자동 생성
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recipe/list")
    public String recipeList(Model model) {
        List<RecipeDto> recipes = recipeService.showRecipeList();
        model.addAttribute("recipes",recipes);
        return "recipe/list";
    }


    // 레시피 목록 페이지(Ajax)
    @GetMapping("/recipe/api/list")
    @ResponseBody
    public List<RecipeDto> recipeListJson(@RequestParam(defaultValue = "ALL") String category,
                                          @RequestParam(defaultValue = "newest") String sort) {
        return recipeService.showRecipeListJson(category, sort);
    }


    // 레시피 상세 페이지
    @GetMapping("/recipe/detail")
    public String showRecipeDetail(String recipeId) {
        return recipeService.showRecipeDetail(recipeId);
    }


    // JPA

    // 레시피 조회 (Ajax)
    // 마이페이지 - 내가 쓴 글/후기 메뉴에서 사용
    @ResponseBody
    @GetMapping("/api/members/mypage/recipes")
    public List<RecipeResponseDto> listByMemberId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<RecipeResponseDto> recipes = new ArrayList<>();
        for (Recipe recipe : recipeService.getRecipeByMemberId(userDetails.getId())) {
            recipes.add(RecipeResponseDto.fromEntity(recipe));
        }

        return recipes;
    }

}