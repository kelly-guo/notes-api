package com.kelly.notesapi.controllers.dtos;

import com.kelly.notesapi.entities.User;

import lombok.Data;

@Data
public class ShareNoteByEmailRequest {

    private String email;
    private Permissions permissions;
    
}
