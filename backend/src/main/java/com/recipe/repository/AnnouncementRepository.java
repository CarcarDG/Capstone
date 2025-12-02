package com.recipe.repository;

import com.recipe.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    @Query("SELECT a FROM Announcement a WHERE a.status = 1 ORDER BY a.createdAt DESC")
    List<Announcement> findAllActive();
    
    List<Announcement> findByUserId(Long userId);
}
