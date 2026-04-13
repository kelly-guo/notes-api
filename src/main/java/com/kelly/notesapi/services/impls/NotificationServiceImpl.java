package com.kelly.notesapi.services.impls;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Notification;
import com.kelly.notesapi.repos.NotificationRepo;
import com.kelly.notesapi.services.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;

    @Override
    public List<Notification> getUserNotifs(Long userId) {
        return notificationRepo.findByUserUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Notification createNotification(Note note) {
       Notification notification = new Notification();
       notification.setMessage("Reminder: " + note.getTitle());
            notification.setCreatedAt(LocalDateTime.now());
            notification.setRead(false);
            notification.setUser(note.getUser());

            return notificationRepo.save(notification);
    }

    public List<Notification> getUnreadNotifs(Long userId){
        return notificationRepo.findByUserUserIdAndReadFalseOrderByCreatedAtDesc(userId);
    }

    @Override
    public void markAsRead(Long noteId) {
        Notification notif = notificationRepo.findById(noteId).orElseThrow();
        notif.setRead(true);
        notificationRepo.save(notif);
        
    }

    @Override
    public void markAllAsRead(Long userId) {
        List<Notification>notifs = notificationRepo.findByUserUserIdAndReadFalse(userId);
        for (Notification n:notifs){
            n.setRead(true);
            
        }
        notificationRepo.saveAll(notifs);
    }

    
    
}
