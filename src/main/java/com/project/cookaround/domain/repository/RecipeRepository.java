package com.project.cookaround.domain.repository;

import com.project.cookaround.domain.entity.Recipe;
import com.project.cookaround.domain.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategory(RecipeCategory category);


/*  == jpa가 자동으로 제공하는 기능 ==
    save(entity)	저장 (insert 또는 update)
    findById(id)	ID로 조회
    findAll()	전체 조회
    deleteById(id)	ID로 삭제
    count()	전체 개수 조회
    existsById(id)	존재 여부 확인
*/
}
