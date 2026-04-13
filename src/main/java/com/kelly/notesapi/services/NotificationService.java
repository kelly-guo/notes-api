package com.kelly.notesapi.services;

import java.util.List;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Notification;

public interface NotificationService {

    List<Notification>getUserNotifs(Long userId);

    Notification createNotification(Note note); 

    List<Notification>getUnreadNotifs(Long userId);

    void markAsRead(Long noteId);

    void markAllAsRead(Long userId);
    
}
