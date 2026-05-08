package com.kelly.notesapi.services.impls;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kelly.notesapi.entities.ActionType;
import com.kelly.notesapi.entities.ActivityLog;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.repos.ActivityLogRepo;
import com.kelly.notesapi.services.ActivityLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService{

    private final ActivityLogRepo activityLogRepo;

    @Override
    public void log(User user, Note note, ActionType action, String details) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setActionType(action);
        activityLog.setDetails(details);
        activityLog.setUser(user);
        activityLog.setNote(note);
        activityLog.setTimeStamp(LocalDateTime.now());

        activityLogRepo.save(activityLog);
    }


    
}
