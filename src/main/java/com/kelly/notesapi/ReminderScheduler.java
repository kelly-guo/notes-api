package com.kelly.notesapi;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.repos.NoteRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final NoteRepo noteRepository;

    @Scheduled(fixedRate=60000)
    public void checkReminders(){
        List<Note> dueNotes = noteRepository.findByReminderAtBeforeAndReminderSentFalseAndDeletedFalse(LocalDateTime.now());
        for (Note note:dueNotes){
            System.out.println("Reminder: " + note.getTitle());

            note.setReminderSent(true);
        }

        noteRepository.saveAll(dueNotes);
    }
    
}