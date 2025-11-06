package com.project.cookaround.domain.recipe.repository;

import com.project.cookaround.domain.recipe.entity.Recipe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepository {

    @PersistenceContext
    private EntityManager em;

    public Long countByMemberId(Long memberId) {
        return em.createQuery("select count(r) from Recipe r where r.member.id = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

    public List<Recipe> findByMemberIdOrderById(Long memberId) {
        return em.createQuery("select r from Recipe r where r.member.id = :memberId order by r.id desc", Recipe.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

}
