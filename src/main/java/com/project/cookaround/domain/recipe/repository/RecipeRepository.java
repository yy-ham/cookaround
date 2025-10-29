package com.project.cookaround.domain.recipe.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {

    @PersistenceContext
    private EntityManager em;

    public Long countByMemberId(Long memberId) {
        return em.createQuery("select count(r) from Recipe r where r.member.id = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

}
