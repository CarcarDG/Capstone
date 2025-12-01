package com.recipe.service;

import com.recipe.entity.Recipe;
import com.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeRepository recipeRepository;
    
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllPublished();
    }
    
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }
    
    public List<Recipe> getRecipesByCategory(Long categoryId) {
        return recipeRepository.findByCategoryId(categoryId);
    }
    
    public List<Recipe> getRecipesByUser(Long userId) {
        return recipeRepository.findByUserId(userId);
    }
    
    public List<Recipe> getFeaturedRecipes() {
        return recipeRepository.findAllFeatured();
    }
    
    public List<Recipe> searchRecipes(String keyword) {
        return recipeRepository.searchByTitle(keyword);
    }
    
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
    
    public Recipe updateRecipe(Long id, Recipe recipe) {
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }
    
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setStatus(-1); // Soft delete
        recipeRepository.save(recipe);
    }
    
    public void incrementViewCount(Long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe != null) {
            recipe.setViewCount(recipe.getViewCount() + 1);
            recipeRepository.save(recipe);
        }
    }
}
