package com.kelly.notesapi.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    
}
