package com.recipe.controller;

import com.recipe.entity.Recipe;
import com.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Recipe Controller - Manages recipes
 * Created: 2025-12-01
 * Updated: 2025-12-01 - Connected to database
 */
@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;
    
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        recipeService.incrementViewCount(id);
        return recipeService.getRecipeById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Recipe>> getRecipesByCategory(@PathVariable Long categoryId) {
        List<Recipe> recipes = recipeService.getRecipesByCategory(categoryId);
        return ResponseEntity.ok(recipes);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recipe>> getRecipesByUser(@PathVariable Long userId) {
        List<Recipe> recipes = recipeService.getRecipesByUser(userId);
        return ResponseEntity.ok(recipes);
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<Recipe>> getFeaturedRecipes() {
        List<Recipe> recipes = recipeService.getFeaturedRecipes();
        return ResponseEntity.ok(recipes);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String keyword) {
        List<Recipe> recipes = recipeService.searchRecipes(keyword);
        return ResponseEntity.ok(recipes);
    }
    
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe created = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        Recipe updated = recipeService.updateRecipe(id, recipe);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }
}
