package com.recipe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Category Controller - Manages recipe categories
 * Created: 2025-12-01
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllCategories() {
        List<Map<String, Object>> categories = new ArrayList<>();
        
        // Mock data - Replace with database queries later
        String[] categoryNames = {
            "Chinese Cuisine", "Western Cuisine", "Japanese Cuisine", "Korean Cuisine",
            "Desserts", "Beverages", "Vegetarian", "Quick Meals"
        };
        
        for (int i = 0; i < categoryNames.length; i++) {
            Map<String, Object> category = new HashMap<>();
            category.put("id", i + 1);
            category.put("name", categoryNames[i]);
            category.put("description", "Category for " + categoryNames[i]);
            categories.add(category);
        }
        
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
        Map<String, Object> category = new HashMap<>();
        category.put("id", id);
        category.put("name", "Category " + id);
        category.put("description", "Description for category " + id);
        return ResponseEntity.ok(category);
    }
}
