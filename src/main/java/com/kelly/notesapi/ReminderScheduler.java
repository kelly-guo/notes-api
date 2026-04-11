package com.kelly.notesapi;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Notification;
import com.kelly.notesapi.repos.NoteRepo;
import com.kelly.notesapi.repos.NotificationRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final NoteRepo noteRepository;
    private final NotificationRepo notificationRepo;

    @Scheduled(fixedRate=60000)
    public void checkReminders(){
        List<Note> dueNotes = noteRepository.findByReminderAtBeforeAndReminderSentFalseAndDeletedFalse(LocalDateTime.now());
        for (Note note:dueNotes){
            Notification notification = new Notification();
            notification.setMessage("Reminder: " + note.getTitle());
            notification.setCreatedAt(LocalDateTime.now());
            notification.setRead(false);
            notification.setUser(note.getUser());

            notificationRepo.save(notification);

            note.setReminderSent(true);
        }

        noteRepository.saveAll(dueNotes);
    }
    
}