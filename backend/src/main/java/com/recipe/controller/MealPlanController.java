package com.recipe.controller;

import com.recipe.entity.MealPlan;
import com.recipe.service.MealPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meal-plans")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MealPlanController {
    
    private final MealPlanService mealPlanService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealPlan>> getUserMealPlans(@PathVariable Long userId) {
        try {
            List<MealPlan> mealPlans = mealPlanService.getUserMealPlans(userId);
            return ResponseEntity.ok(mealPlans);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<MealPlan>> getMealPlansByDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByDate(userId, date);
        return ResponseEntity.ok(mealPlans);
    }
    
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<MealPlan>> getMealPlansByDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<MealPlan> mealPlans = mealPlanService.getMealPlansByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(mealPlans);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long id) {
        return mealPlanService.getMealPlanById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(@RequestBody MealPlan mealPlan) {
        MealPlan created = mealPlanService.createMealPlan(mealPlan);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMealPlan(@PathVariable Long id, @RequestBody MealPlan mealPlan) {
        MealPlan updated = mealPlanService.updateMealPlan(id, mealPlan);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        mealPlanService.deleteMealPlan(id);
        return ResponseEntity.ok().build();
    }
}
