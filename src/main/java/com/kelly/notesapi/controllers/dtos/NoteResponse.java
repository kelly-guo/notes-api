package com.kelly.notesapi.controllers.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteResponse {
    private Long id;
    private String title;
    private String contents;
    private boolean pinned;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;




    
}
