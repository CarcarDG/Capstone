package com.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meal_plan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;
    
    @Column(name = "plan_date", nullable = false)
    private LocalDate planDate;
    
    @Column(name = "meal_type", length = 20)
    private String mealType; // breakfast, lunch, dinner, snack
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
