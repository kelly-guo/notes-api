package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class UpdateNoteRequest {

    private String title;

    private String content;

    private boolean archived;

    private boolean pinned;


    
}
