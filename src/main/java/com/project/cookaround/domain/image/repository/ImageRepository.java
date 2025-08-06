package com.project.cookaround.domain.image.repository;

import com.project.cookaround.domain.image.entity.Image;
import com.project.cookaround.domain.image.entity.ImageContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImageRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public List<Image> findByContentTypeAndContentId(ImageContentType contentType, Long contentId) {
        List<Image> images = em.createQuery("select i from Image i where i.contentType = :contentType and i.contentId = :contentId", Image.class)
                .setParameter("contentType", contentType)
                .setParameter("contentId", contentId)
                .getResultList();
        return images;
    }

    public Optional<Image> findByContentTypeAndContentIdOrderByIdAsc(ImageContentType contentType, Long contentId) {
        List<Image> images = em.createQuery("select i from Image i where i.contentType = :contentType and i.contentId = :contentId order by i.id asc", Image.class)
                .setParameter("contentType", contentType)
                .setParameter("contentId", contentId)
                .getResultList();
        return images.stream().findAny();
    }

}
