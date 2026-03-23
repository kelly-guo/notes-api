package com.kelly.notesapi.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Note;

public interface NoteRepo extends JpaRepository<Note, Long>{
    List<Note>findByUserId(Long userId);

    List<Note> findByUserIdAndPinned(Long userId, Boolean pinned);

    List<Note> findByUserIdAndArchived(Long userId, Boolean archived);

    List<Note> findByUserIdAndArchivedAndPinned(Long userId, Boolean archived, Boolean pinned);



    
}
