package com.kelly.notesapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.CreateNoteRequest;
import com.kelly.notesapi.controllers.dtos.NoteResponse;
import com.kelly.notesapi.controllers.dtos.UpdateNoteRequest;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.mappers.NoteMapper;
import com.kelly.notesapi.services.NoteService;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NotesController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper=noteMapper;
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody CreateNoteRequest createNoteRequest, Authentication auth){
        User user = (User) auth.getPrincipal();
        NoteResponse note = noteMapper.toDto(noteService.createNote(user, createNoteRequest.getTitle(), createNoteRequest.getContent()));
        return ResponseEntity.ok(note);

    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes(Authentication auth, @RequestParam(required=false) Boolean archived, @RequestParam(required=false) Boolean pinned){
        User user = (User) auth.getPrincipal();
        List<NoteResponse> notes = noteService.getUserNotes(user,pinned,archived).stream().map(noteMapper::toDto).toList();
        return ResponseEntity.ok(notes);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable Long id, Authentication auth){
        User user = (User) auth.getPrincipal();
        NoteResponse note = noteMapper.toDto(noteService.getByNoteId(id, user));
        return ResponseEntity.ok(note);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, UpdateNoteRequest updateNoteRequest, Authentication auth){
        User user = (User) auth.getPrincipal();
        //ADD FULL UPDATE
        NoteResponse note = noteMapper.toDto(noteService.updateNote(id, updateNoteRequest.getTitle(), updateNoteRequest.getContent(), user, updateNoteRequest.isPinned(), updateNoteRequest.isArchived()));
        return ResponseEntity.ok(note);


    }




    
    
}
