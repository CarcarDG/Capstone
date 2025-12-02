package com.recipe.service;

import com.recipe.entity.Announcement;
import com.recipe.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
    
    private final AnnouncementRepository announcementRepository;
    
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAllActive();
    }
    
    public Optional<Announcement> getAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }
    
    public List<Announcement> getAnnouncementsByUser(Long userId) {
        return announcementRepository.findByUserId(userId);
    }
    
    public Announcement createAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
    
    public Announcement updateAnnouncement(Long id, Announcement announcement) {
        announcement.setId(id);
        return announcementRepository.save(announcement);
    }
    
    public void deleteAnnouncement(Long id) {
        Announcement announcement = announcementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Announcement not found"));
        announcement.setStatus(-1);
        announcementRepository.save(announcement);
    }
}
