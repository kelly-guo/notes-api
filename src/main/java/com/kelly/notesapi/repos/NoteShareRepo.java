package com.kelly.notesapi.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.NoteShare;
import com.kelly.notesapi.entities.User;

public interface NoteShareRepo extends JpaRepository<NoteShare,Long>{

    Page<NoteShare>findByUser(User user, Pageable pageable);

    List<NoteShare>findByNote(Note note);

    Optional<NoteShare>findByNoteAndUser(Note note, User user);

    
} 
