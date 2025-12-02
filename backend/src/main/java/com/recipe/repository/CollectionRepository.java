package com.recipe.repository;

import com.recipe.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    
    List<Collection> findByUserId(Long userId);
    
    Optional<Collection> findByUserIdAndRecipeId(Long userId, Long recipeId);
    
    void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
}
