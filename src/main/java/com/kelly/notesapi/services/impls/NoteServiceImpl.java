package com.kelly.notesapi.services.impls;

import java.time.LocalDateTime;
import java.util.List;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.repos.NoteRepo;
import com.kelly.notesapi.repos.UserRepo;
import com.kelly.notesapi.services.NoteService;

public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final UserRepo userRepo;
    public NoteServiceImpl(NoteRepo noteRepo, UserRepo userRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }
    @Override
    public Note createNote(User user, String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setUser(user);
        note.setContents(content);
        note.setCreatedAt(LocalDateTime.now());
        return noteRepo.save(note);


    }
    @Override
    public List<Note> getUserNotes(Long userId) {
       return noteRepo.findByUserId(userId);
    }
    @Override
    public Note getByNoteId(Long id, User user) {
        Note note= noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("User does not match");
        }
        return note;
    }
    @Override
    public Note updateNote(Long noteId, String title, String content, User user) {
        Note note= noteRepo.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("User does not match");
        }
        note.setContents(content);
        note.setTitle(title);
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepo.save(note);
    }
    @Override
    public void deleteNote(Long id, User user) {
        Note note= noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("User does not match");
        }
        noteRepo.delete(note);
    }

    


    
}
