package com.kelly.notesapi.repos;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;

public interface NoteRepo extends JpaRepository<Note, Long>{
    Page<Note>findByUserIdAndDeletedTrue(Long userId, Pageable page);

    Page<Note>findByUserAndDeletedFalse(Long userId, Pageable page);

    Page<Note> findByUserIdAndPinned(Long userId, Boolean pinned,Pageable page);

    Page<Note> findByUserIdAndArchived(Long userId, Boolean archived,Pageable page);

    Page<Note> findByUserIdAndArchivedAndPinned(Long userId, Boolean archived, Boolean pinned,Pageable page);

    Page<Note> findByUserIdAndTags_Name(Long userId, String tagName, Pageable pageable);

    Optional<Note> findByNoteIdAndUserAndDeletedFalse(Long noteId, User user);

    Page<Note> findByUserAndDeletedFalseAndReminderAtAfter(
    User user,
    LocalDateTime now,
    Pageable pageable
);



    
}
