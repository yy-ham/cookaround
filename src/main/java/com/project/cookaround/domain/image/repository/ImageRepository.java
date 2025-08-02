package com.project.cookaround.domain.image.repository;

import com.project.cookaround.domain.image.entity.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ImageRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

}
