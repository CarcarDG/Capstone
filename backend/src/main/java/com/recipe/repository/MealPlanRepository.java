package com.recipe.repository;

import com.recipe.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    
    List<MealPlan> findByUserId(Long userId);
    
    List<MealPlan> findByUserIdAndPlanDate(Long userId, LocalDate planDate);
    
    @Query("SELECT m FROM MealPlan m WHERE m.userId = ?1 AND m.planDate BETWEEN ?2 AND ?3 ORDER BY m.planDate, m.mealType")
    List<MealPlan> findByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
}
