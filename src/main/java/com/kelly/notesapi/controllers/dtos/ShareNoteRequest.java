package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class ShareNoteRequest {
    private Long userId;
    private Permissions permissions;
    
}
