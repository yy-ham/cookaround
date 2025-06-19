package com.project.cookaround.domain.repository;

import com.project.cookaround.domain.entity.Recipe;
import com.project.cookaround.domain.entity.RecipeCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository {

    @PersistenceContext
    private EntityManager em;

    // == 레시피 목록 띄우기 == //
    public List<Recipe> findAll() {
        return em.createQuery("SELECT r FROM Recipe r", Recipe.class)
                .getResultList();
    }

    // == 카테고리에 따른 목록 띄우기 == //
    public List<Recipe> findByCategory(String category) {
        return em.createQuery("SELECT r FROM Recipe r WHERE r.category = :category", Recipe.class)
                .setParameter("category", RecipeCategory.valueOf(category.toUpperCase()))
                .getResultList();
    }


}
