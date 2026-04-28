package com.kelly.notesapi.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kelly.notesapi.controllers.dtos.Permissions;
import com.kelly.notesapi.controllers.dtos.SharedNoteResponse;
import com.kelly.notesapi.controllers.dtos.UpdatePermissionsRequest;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Tag;
import com.kelly.notesapi.entities.User;

public interface NoteService {

    Note createNote(User user, String title, String content, List<String>tags, LocalDateTime reminder);

    Page<Note> getUserNotes(User user, Boolean pinned, Boolean archived, String tag, Pageable pageable);

    Note getByNoteId(Long id, User user);

    Note updateNote(Long noteId, String title, String content, User user, boolean pinned, boolean archived, List<String>tags, LocalDateTime reminder);

    Set<Tag> processTags(List<String>names);

    Page<Note> getTrashedNotes(Long userId, Pageable page);

    void moveToTrash(Long userId, Long noteId);

    void restoreNote(Long noteId, Long userId);

    void permaDelete(Long noteId, Long userId);

    void removeReminder(Long noteId, Long userId);

    Page<Note> getUpcomingReminderNotes(Long userId, Pageable page);

      Page<Note> getReminderNotes(Long userId, Pageable page);

      void shareNote(Long userId, Long noteId, Permissions permissions);

      Page<Note>getSharedNotes(User user, Pageable pageable);

      boolean canEdit(User user, Note note);

      void removeAccess(Long noteId, Long userId, User currentUser);

      void editPermissions(UpdatePermissionsRequest updatePermissionsRequest, User currentUser, Long noteId);

      List<SharedNoteResponse> getShares(Long noteId, User currentUser);

      void shareNoteByEmail(User currentUser, Long noteId, String email, Permissions permissions);









    
}
