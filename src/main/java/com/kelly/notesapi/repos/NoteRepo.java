package com.kelly.notesapi.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Note;

public interface NoteRepo extends JpaRepository<Note, Long>{
    Page<Note>findByUserId(Long userId, Pageable page);

    Page<Note> findByUserIdAndPinned(Long userId, Boolean pinned,Pageable page);

    Page<Note> findByUserIdAndArchived(Long userId, Boolean archived,Pageable page);

    Page<Note> findByUserIdAndArchivedAndPinned(Long userId, Boolean archived, Boolean pinned,Pageable page);



    
}
