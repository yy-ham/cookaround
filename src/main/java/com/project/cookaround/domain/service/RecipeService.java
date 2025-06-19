package com.project.cookaround.domain.service;

import com.project.cookaround.domain.entity.Recipe;
import com.project.cookaround.domain.entity.RecipeCategory;
import com.project.cookaround.domain.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 필드만 골라서 생성자 자동 생성
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategory(RecipeCategory.valueOf(category));
        // category가 자꾸 안넘어가서 String category에서 RecipeCategory.valueOf(category)로 바꾸니까 됐음
        // Repository에서 보내는 파라미터도 RecipeCategory category로 바꿔주고 Service도 바꿔줌.
        // 근데 Controller에서는 그대로 String으로 받았는데... 왜 되는지 모르겠음
        // 애초에 받을때 String으로 받아서 'ALL'이랑 비교해서 그런가...?
    }
}
