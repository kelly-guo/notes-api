package com.kelly.notesapi.services;

import com.kelly.notesapi.entities.ActionType;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.User;

public interface ActivityLogService {

    public void log(User user, Note note, ActionType action, String details);
    
}
