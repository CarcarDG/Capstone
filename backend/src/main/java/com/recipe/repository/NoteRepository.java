package com.recipe.repository;

import com.recipe.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
    @Query("SELECT n FROM Note n WHERE n.status = 1 ORDER BY n.createdAt DESC")
    List<Note> findAllActive();
    
    List<Note> findByUserId(Long userId);
}
