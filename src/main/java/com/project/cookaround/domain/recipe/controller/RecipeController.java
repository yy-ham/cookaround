package com.project.cookaround.domain.recipe.controller;

import com.project.cookaround.domain.recipe.dto.RecipeDto;
import com.project.cookaround.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}