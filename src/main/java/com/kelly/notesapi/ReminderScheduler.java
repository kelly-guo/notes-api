package com.kelly.notesapi;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Notification;
import com.kelly.notesapi.repos.NoteRepo;
import com.kelly.notesapi.repos.NotificationRepo;
import com.kelly.notesapi.services.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final NoteRepo noteRepository;
    private final NotificationRepo notificationRepo;
    private final NotificationService notificationService;

    @Scheduled(fixedRate=60000)
    public void checkReminders(){
        List<Note> dueNotes = noteRepository.findByReminderAtBeforeAndReminderSentFalseAndDeletedFalse(LocalDateTime.now());
        for (Note note:dueNotes){
            notificationService.createNotification(note);

            note.setReminderSent(true);
        }

        noteRepository.saveAll(dueNotes);
    }
    
}