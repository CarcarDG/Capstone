package com.recipe.controller;

import com.recipe.entity.Announcement;
import com.recipe.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AnnouncementController {
    
    private final AnnouncementService announcementService;
    
    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        try {
            List<Announcement> announcements = announcementService.getAllAnnouncements();
            return ResponseEntity.ok(announcements);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        return announcementService.getAnnouncementById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Announcement>> getAnnouncementsByUser(@PathVariable Long userId) {
        List<Announcement> announcements = announcementService.getAnnouncementsByUser(userId);
        return ResponseEntity.ok(announcements);
    }
    
    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
        Announcement created = announcementService.createAnnouncement(announcement);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {
        Announcement updated = announcementService.updateAnnouncement(id, announcement);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok().build();
    }
}
