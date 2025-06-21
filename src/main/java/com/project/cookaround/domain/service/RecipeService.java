package com.project.cookaround.domain.service;

import com.project.cookaround.domain.entity.Recipe;
import com.project.cookaround.domain.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 필드만 골라서 생성자 자동 생성
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    // 레시피 목록 조회
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    // 카테고리에 따른 목록 조회
    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategory(category);
    }

    // 레시피 상세페이지 조회
    public Recipe findById(Long id) {
        return recipeRepository.findById(id);
    }
}
