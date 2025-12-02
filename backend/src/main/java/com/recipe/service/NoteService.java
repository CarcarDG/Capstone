package com.recipe.service;

import com.recipe.entity.Note;
import com.recipe.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    
    private final NoteRepository noteRepository;
    
    public List<Note> getAllNotes() {
        return noteRepository.findAllActive();
    }
    
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }
    
    public List<Note> getNotesByUser(Long userId) {
        return noteRepository.findByUserId(userId);
    }
    
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }
    
    public Note updateNote(Long id, Note note) {
        note.setId(id);
        return noteRepository.save(note);
    }
    
    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
        note.setStatus(-1);
        noteRepository.save(note);
    }
    
    public void incrementViewCount(Long id) {
        Note note = noteRepository.findById(id).orElse(null);
        if (note != null) {
            note.setViewCount(note.getViewCount() + 1);
            noteRepository.save(note);
        }
    }
}
