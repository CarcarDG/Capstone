package com.recipe.service;

import com.recipe.entity.MealPlan;
import com.recipe.repository.MealPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MealPlanService {
    
    private final MealPlanRepository mealPlanRepository;
    
    public List<MealPlan> getUserMealPlans(Long userId) {
        return mealPlanRepository.findByUserId(userId);
    }
    
    public List<MealPlan> getMealPlansByDate(Long userId, LocalDate date) {
        return mealPlanRepository.findByUserIdAndPlanDate(userId, date);
    }
    
    public List<MealPlan> getMealPlansByDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return mealPlanRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }
    
    public Optional<MealPlan> getMealPlanById(Long id) {
        return mealPlanRepository.findById(id);
    }
    
    public MealPlan createMealPlan(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }
    
    public MealPlan updateMealPlan(Long id, MealPlan mealPlan) {
        mealPlan.setId(id);
        return mealPlanRepository.save(mealPlan);
    }
    
    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
}
