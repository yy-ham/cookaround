package com.project.cookaround.domain.controller;

import com.project.cookaround.domain.entity.Recipe;
import com.project.cookaround.domain.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 필드만 골라서 생성자 자동 생성
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    // 레시피 목록 조회
    @GetMapping("/recipe/list")
    public String showRecipeList(Model model) {
        List<Recipe> recipeList = recipeService.findAll();
        model.addAttribute("recipeList", recipeList);
        return "recipe/list";
    }

    // 카테고리에 따른 목록 조회(Ajax)
    @GetMapping("/recipe/api/list")
    @ResponseBody
    public List<Recipe> showRecipeListJson(@RequestParam(required = false) String category) {
        if(category == null || category.equals("ALL")) {
            return recipeService.findAll();
        }
        return recipeService.findByCategory(category);
    }

    // 레시피 상세페이지 조회
    @GetMapping("/recipe/detail")
    public String recipeDetail(@RequestParam("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/detail";
    }

}