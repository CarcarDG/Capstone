package com.recipe.controller;

import com.recipe.entity.Note;
import com.recipe.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NoteController {
    
    private final NoteService noteService;
    
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        try {
            List<Note> notes = noteService.getAllNotes();
            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        noteService.incrementViewCount(id);
        return noteService.getNoteById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getNotesByUser(@PathVariable Long userId) {
        List<Note> notes = noteService.getNotesByUser(userId);
        return ResponseEntity.ok(notes);
    }
    
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note created = noteService.createNote(note);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        Note updated = noteService.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
