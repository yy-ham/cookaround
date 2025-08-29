package com.project.cookaround.domain.cookingtip.repository;

import com.project.cookaround.domain.cookingtip.entity.CookingTip;
import com.project.cookaround.domain.cookingtip.entity.CookingTipCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CookingTipRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(CookingTip cookingTip) {
        em.persist(cookingTip);
    }

    // 요리팁 전체 목록 조회 (최신순)
    public List<CookingTip> findAllOrderByCreatedAtDesc() {
        List<CookingTip> cookingTips = em.createQuery("select c from CookingTip c order by c.createdAt desc", CookingTip.class)
                .getResultList();
        return cookingTips;
    }

    // 요리팁 전체 목록 조회 (조회순)
    public List<CookingTip> findAllOrderByViewCountDesc() {
        List<CookingTip> cookingTips = em.createQuery("select c from CookingTip c order by c.viewCount desc", CookingTip.class)
                .getResultList();
        return cookingTips;
    }

    // 요리팁 전체 목록 조회 (좋아요순)
    public List<CookingTip> findAllOrderByLikeCountDesc() {
        List<CookingTip> cookingTips = em.createQuery("select c from CookingTip c order by c.likeCount desc", CookingTip.class)
                .getResultList();
        return cookingTips;
    }

    public Optional<CookingTip> findById(Long id) {
        List<CookingTip> cookingTips = em.createQuery("select c from CookingTip c where c.id = :id", CookingTip.class)
                    .setParameter("id", id)
                    .getResultList();
        return cookingTips.stream().findAny();
    }

    public List<CookingTip> findByCategory(CookingTipCategory category) {
        List<CookingTip> cookingTips = em.createQuery("select c from CookingTip c where c.category = :category", CookingTip.class)
                .setParameter("category", category)
                .getResultList();
        return cookingTips;
    }

    public void delete(CookingTip cookingTip) {
        em.remove(cookingTip);
    }

}
