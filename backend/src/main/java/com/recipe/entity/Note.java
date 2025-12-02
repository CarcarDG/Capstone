package com.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "cover_image", length = 255)
    private String coverImage;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "view_count")
    private Integer viewCount = 0;
    
    @Column(name = "like_count")
    private Integer likeCount = 0;
    
    @Column(nullable = false)
    private Integer status = 1;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (viewCount == null) viewCount = 0;
        if (likeCount == null) likeCount = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
