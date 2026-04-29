package com.kelly.notesapi.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.CreateNoteRequest;
import com.kelly.notesapi.controllers.dtos.NoteResponse;
import com.kelly.notesapi.controllers.dtos.ShareNoteByEmailRequest;
import com.kelly.notesapi.controllers.dtos.ShareNoteRequest;
import com.kelly.notesapi.controllers.dtos.SharedNoteResponse;
import com.kelly.notesapi.controllers.dtos.UpdateNoteRequest;
import com.kelly.notesapi.controllers.dtos.UpdatePermissionsRequest;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.mappers.NoteMapper;
import com.kelly.notesapi.services.NoteService;

import jakarta.validation.Valid;

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
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest createNoteRequest, Authentication auth){
        User user = (User) auth.getPrincipal();
        NoteResponse note = noteMapper.toDto(noteService.createNote(user, createNoteRequest.getTitle(), createNoteRequest.getContent(),createNoteRequest.getTags(), createNoteRequest.getReminder()));
        return ResponseEntity.ok(note);

    }

    @GetMapping
    public ResponseEntity<Page<NoteResponse>> getAllNotes(Authentication auth, @RequestParam(required=false) Boolean archived, @RequestParam(required=false) Boolean pinned, @RequestParam(required=false) String tag, Pageable pageable){
        User user = (User) auth.getPrincipal();
        Page<NoteResponse> notes = noteService.getUserNotes(user,pinned,archived, tag, pageable).map(noteMapper::toDto);
        return ResponseEntity.ok(notes);
    }

    @GetMapping
    public ResponseEntity<Page<NoteResponse>> getTrashNotes(Authentication auth, Pageable pageable){
        User user = (User) auth.getPrincipal();
        Long userId = user.getUserId();
        Page<NoteResponse> notes = noteService.getTrashedNotes(userId, pageable).map(noteMapper::toDto);
        return ResponseEntity.ok(notes);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable Long id, Authentication auth){
        User user = (User) auth.getPrincipal();
        NoteResponse note = noteMapper.toDto(noteService.getByNoteId(id, user));
        return ResponseEntity.ok(note);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, @Valid @RequestBody UpdateNoteRequest updateNoteRequest, Authentication auth){
        User user = (User) auth.getPrincipal();
        //ADD FULL UPDATE
        NoteResponse note = noteMapper.toDto(noteService.updateNote(id, updateNoteRequest.getTitle(), updateNoteRequest.getContent(), user, updateNoteRequest.isPinned(), updateNoteRequest.isArchived(), updateNoteRequest.getTags(), updateNoteRequest.getReminder()));
        return ResponseEntity.ok(note);


    }

    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId, Authentication auth){
        User user = (User) auth.getPrincipal();
        Long userId=user.getUserId();
        noteService.moveToTrash(noteId, userId);
        return ResponseEntity.noContent().build();
    }

     @PatchMapping(path = "/{noteId}/restore")
    public ResponseEntity<Void> restoreNote(@PathVariable Long noteId, Authentication auth){
        User user = (User) auth.getPrincipal();
        Long userId=user.getUserId();
        noteService.restoreNote(noteId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{noteId}/force")
    public ResponseEntity<Void> forceNote(@PathVariable Long noteId, Authentication auth){
        User user = (User) auth.getPrincipal();
        Long userId=user.getUserId();
        noteService.permaDelete(noteId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/upcoming/reminders")
    public ResponseEntity<Page<NoteResponse>> getUpcomingReminders(@RequestParam Long userId, Pageable pageable){
        return ResponseEntity.ok(noteService.getUpcomingReminderNotes(userId, pageable).map(noteMapper::toDto));
    }

    @PatchMapping(path = "/{noteId}/reminders/remove")
    public ResponseEntity<Void> removeReminders(@PathVariable Long noteId, @RequestParam Long userId){
        noteService.removeReminder(noteId, userId);
        return ResponseEntity.ok().build();

    }

    @GetMapping(path = "/reminders")
    public ResponseEntity<Page<NoteResponse>> getReminders(@RequestParam Long userId, Pageable pageable){
        return ResponseEntity.ok(noteService.getReminderNotes(userId, pageable).map(noteMapper::toDto));
    }

    @GetMapping("/shared")
    public ResponseEntity<Page<NoteResponse>> getSharedNotes(Authentication authentication, Pageable pageable){
        User user=(User) authentication.getPrincipal();
        return ResponseEntity.ok(noteService.getSharedNotes(user,pageable).map(noteMapper::toDto));
    }

    @PostMapping("/{noteId}/share")
    public void shareNote(Long noteId, @RequestBody ShareNoteRequest shareNoteRequest){ 
        noteService.shareNote(shareNoteRequest.getUserId(), noteId, shareNoteRequest.getPermissions());  
    }

    @DeleteMapping("/{noteId}/share/{userId}")
    public void deleteShare(@PathVariable Long noteId, @PathVariable Long userId, Authentication authentication){
        User user=(User) authentication.getPrincipal();
        noteService.removeAccess(noteId, userId, user);
    }

    @PatchMapping("/{noteId}/share")
    public void updatePermissions(@RequestBody UpdatePermissionsRequest updatePermissionsRequest, @PathVariable Long noteId, Authentication authentication){
        User user=(User) authentication.getPrincipal();
        noteService.editPermissions(updatePermissionsRequest,user,noteId);

    }


    @GetMapping("/{noteId}/get-shares")
    public List<SharedNoteResponse> getShares(@PathVariable Long noteId, Authentication authentication){
         User user=(User) authentication.getPrincipal();
         return noteService.getShares(noteId,user);
    }

    @PostMapping("/{noteId}/share-email")
    public void shareNoteByEmail(@PathVariable Long noteId, Authentication auth, ShareNoteByEmailRequest shareNoteByEmailRequest){
        User user=(User) auth.getPrincipal();
        noteService.shareNoteByEmail(user,noteId,shareNoteByEmailRequest.getEmail(), shareNoteByEmailRequest.getPermissions());

    }






    
    
}
