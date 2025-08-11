package com.project.cookaround.domain.likes.repository;

import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LikesRepository {

    @PersistenceContext
    private EntityManager em;

    public boolean existsByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        Long count = em.createQuery("select count(l) from Likes l where l.member.id = :memberId and l.contentType = :contentType and l.contentId = :contentId", Long.class)
                .setParameter("memberId", memberId)
                .setParameter("contentType", contentType)
                .setParameter("contentId", contentId)
                .getSingleResult();
        return count > 0;
    }

    public void save(Likes like) {
        em.persist(like);
    }

}
