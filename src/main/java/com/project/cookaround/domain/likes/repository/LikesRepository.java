package com.project.cookaround.domain.likes.repository;

import com.project.cookaround.domain.likes.entity.Likes;
import com.project.cookaround.domain.likes.entity.LikesContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LikesRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Likes> findById(Long id) {
        List<Likes> likes = em.createQuery("select l from Likes l where l.id = :id", Likes.class)
                .setParameter("id", id)
                .getResultList();
        return likes.stream().findAny();
    }

    public Optional<Likes> findByMemberIdAndContentTypeAndContentId(Long memberId, LikesContentType contentType, Long contentId) {
        List<Likes> likes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.contentType = :contentType and l.contentId = :contentId", Likes.class)
                .setParameter("memberId", memberId)
                .setParameter("contentType", contentType)
                .setParameter("contentId", contentId)
                .getResultList();
        return likes.stream().findAny();
    }

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

    public void delete(Likes like) {
        em.remove(like);
    }

    public List<Likes> findByContentTypeAndContentId(LikesContentType contentType, Long contentId) {
        return em.createQuery("select l from Likes l where l.contentType = :contentType and l.contentId = :contentId", Likes.class)
                .setParameter("contentType", contentType)
                .setParameter("contentId", contentId)
                .getResultList();
    }

    public List<Likes> findByMemberIdAndContentType(Long memberId, LikesContentType contentType) {
        return em.createQuery("select l from Likes l where l.member.id = :memberId and l.contentType = :contentType", Likes.class)
                .setParameter("memberId", memberId)
                .setParameter("contentType", contentType)
                .getResultList();
    }

}
