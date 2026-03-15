package com.kelly.notesapi.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Note;

public interface NoteRepo extends JpaRepository<Long, Note>{
    List<Note>findByUserId(Long userId);

    
}
