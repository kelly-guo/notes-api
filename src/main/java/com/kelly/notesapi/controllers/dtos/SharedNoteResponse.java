package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class SharedNoteResponse {
    private Long userId;
    private String email;
    private Permissions permissions;
    
}
