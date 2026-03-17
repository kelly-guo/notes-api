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
    public Note createNote(Long userId, String title, String content) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
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
    public Note getByNoteId(Long id) {
        return noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
    }
    @Override
    public Note updateNote(Long userId, String title, String content) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateNote'");
    }
    @Override
    public void deleteNote(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteNote'");
    }

    


    
}
