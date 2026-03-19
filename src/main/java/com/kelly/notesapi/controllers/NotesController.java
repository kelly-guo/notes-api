package com.kelly.notesapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.CreateNoteRequest;
import com.kelly.notesapi.controllers.dtos.NoteResponse;
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



    
    
}
