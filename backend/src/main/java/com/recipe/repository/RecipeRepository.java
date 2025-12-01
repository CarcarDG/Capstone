package com.recipe.repository;

import com.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    List<Recipe> findByCategoryId(Long categoryId);
    
    List<Recipe> findByUserId(Long userId);
    
    List<Recipe> findByStatus(Integer status);
    
    @Query("SELECT r FROM Recipe r WHERE r.status = 1 ORDER BY r.createdAt DESC")
    List<Recipe> findAllPublished();
    
    @Query("SELECT r FROM Recipe r WHERE r.isFeatured = 1 AND r.status = 1 ORDER BY r.createdAt DESC")
    List<Recipe> findAllFeatured();
    
    @Query("SELECT r FROM Recipe r WHERE r.title LIKE %?1% AND r.status = 1")
    List<Recipe> searchByTitle(String keyword);
}
