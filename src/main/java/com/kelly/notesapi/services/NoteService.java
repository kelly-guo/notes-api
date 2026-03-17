package com.kelly.notesapi.services;

import java.util.List;

import com.kelly.notesapi.entities.Note;

public interface NoteService {

    Note createNote(Long userId, String title, String content);

    List<Note> getUserNotes(Long userId);

    Note getByNoteId(Long id);

    Note updateNote(Long userId, String title, String content);

    void deleteNote(Long id);







    
}
