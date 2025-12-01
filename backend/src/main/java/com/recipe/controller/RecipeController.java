package com.recipe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Recipe Controller - Manages recipes
 * Created: 2025-12-01
 */
@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllRecipes() {
        List<Map<String, Object>> recipes = new ArrayList<>();
        
        // Mock data - Replace with database queries later
        String[] recipeNames = {
            "Kung Pao Chicken", "Mapo Tofu", "Braised Pork Belly", "Sweet and Sour Ribs",
            "Tomato Scrambled Eggs", "Fish Flavored Shredded Pork", "Boiled Fish", "Twice Cooked Pork"
        };
        
        for (int i = 0; i < recipeNames.length; i++) {
            Map<String, Object> recipe = new HashMap<>();
            recipe.put("id", i + 1);
            recipe.put("title", recipeNames[i]);
            recipe.put("description", "Detailed recipe for " + recipeNames[i]);
            recipe.put("difficulty", i % 3 == 0 ? "Easy" : i % 3 == 1 ? "Medium" : "Hard");
            recipe.put("cookingTime", (i + 1) * 10);
            recipe.put("servings", i % 4 + 2);
            recipe.put("categoryId", i % 8 + 1);
            recipes.add(recipe);
        }
        
        return ResponseEntity.ok(recipes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRecipeById(@PathVariable Long id) {
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("id", id);
        recipe.put("title", "Recipe " + id);
        recipe.put("description", "This is a delicious dish");
        recipe.put("difficulty", "Medium");
        recipe.put("cookingTime", 30);
        recipe.put("servings", 4);
        recipe.put("ingredients", "List of ingredients");
        recipe.put("steps", "Cooking steps");
        return ResponseEntity.ok(recipe);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Map<String, Object>>> getRecipesByCategory(@PathVariable Long categoryId) {
        List<Map<String, Object>> recipes = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Map<String, Object> recipe = new HashMap<>();
            recipe.put("id", categoryId * 10 + i);
            recipe.put("title", "Recipe " + (i + 1) + " from Category " + categoryId);
            recipe.put("categoryId", categoryId);
            recipes.add(recipe);
        }
        
        return ResponseEntity.ok(recipes);
    }
}
