package com.kelly.notesapi.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
List<Notification> findByUserUserIdOrderByCreatedAtDesc(Long userId);

List<Notification> findByUserUserIdAndReadFalseOrderByCreatedAtDesc(Long userId);

List<Notification> findByUserUserIdAndReadFalse(Long userId);
    
}
