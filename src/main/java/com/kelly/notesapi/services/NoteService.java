package com.kelly.notesapi.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Tag;
import com.kelly.notesapi.entities.User;

public interface NoteService {

    Note createNote(User user, String title, String content, List<String>tags);

    Page<Note> getUserNotes(User user, Boolean pinned, Boolean archived, String tag, Pageable pageable);

    Note getByNoteId(Long id, User user);

    Note updateNote(Long noteId, String title, String content, User user, boolean pinned, boolean archived, List<String>tags);

    Set<Tag> processTags(List<String>names);

    Page<Note> getTrashedNotes(Long userId, Pageable page);

    void moveToTrash(Long userId, Long noteId);

    void restoreNote(Long noteId, Long userId);

    void permaDelete(Long noteId, Long userId);









    
}
