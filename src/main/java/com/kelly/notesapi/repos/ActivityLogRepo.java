package com.kelly.notesapi.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.ActivityLog;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;

public interface ActivityLogRepo extends JpaRepository<ActivityLog, Long>{
    Page<ActivityLog> findByNote(Note note, Pageable pageable);

    Page<ActivityLog> findByUser(User user, Pageable pageable);
    
}
