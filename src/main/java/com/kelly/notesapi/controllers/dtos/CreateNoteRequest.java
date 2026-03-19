package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class CreateNoteRequest {

    private String title;

    private String content;
    
}
