package com.kelly.notesapi.services;

import java.util.List;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;

public interface NoteService {

    Note createNote(User user, String title, String content);

    List<Note> getUserNotes(Long userId);

    Note getByNoteId(Long id, User user);

    Note updateNote(Long noteId, String title, String content, User user);

    void deleteNote(Long id, User user);







    
}
