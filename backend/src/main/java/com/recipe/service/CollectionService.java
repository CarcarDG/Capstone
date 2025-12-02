package com.recipe.service;

import com.recipe.entity.Collection;
import com.recipe.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {
    
    private final CollectionRepository collectionRepository;
    
    public List<Collection> getUserCollections(Long userId) {
        return collectionRepository.findByUserId(userId);
    }
    
    public Collection addToCollection(Long userId, Long recipeId) {
        // Check if already collected
        if (collectionRepository.findByUserIdAndRecipeId(userId, recipeId).isPresent()) {
            throw new RuntimeException("Recipe already in collection");
        }
        
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setRecipeId(recipeId);
        return collectionRepository.save(collection);
    }
    
    @Transactional
    public void removeFromCollection(Long userId, Long recipeId) {
        collectionRepository.deleteByUserIdAndRecipeId(userId, recipeId);
    }
    
    public boolean isCollected(Long userId, Long recipeId) {
        return collectionRepository.findByUserIdAndRecipeId(userId, recipeId).isPresent();
    }
}
