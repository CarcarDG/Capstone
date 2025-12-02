package com.recipe.controller;

import com.recipe.entity.Collection;
import com.recipe.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CollectionController {
    
    private final CollectionService collectionService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Collection>> getUserCollections(@PathVariable Long userId) {
        try {
            List<Collection> collections = collectionService.getUserCollections(userId);
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Collection> addToCollection(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long recipeId = request.get("recipeId");
            Collection collection = collectionService.addToCollection(userId, recipeId);
            return ResponseEntity.ok(collection);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/user/{userId}/recipe/{recipeId}")
    public ResponseEntity<Void> removeFromCollection(@PathVariable Long userId, @PathVariable Long recipeId) {
        try {
            collectionService.removeFromCollection(userId, recipeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/user/{userId}/recipe/{recipeId}/check")
    public ResponseEntity<Map<String, Boolean>> checkCollection(@PathVariable Long userId, @PathVariable Long recipeId) {
        boolean isCollected = collectionService.isCollected(userId, recipeId);
        return ResponseEntity.ok(Map.of("isCollected", isCollected));
    }
}
