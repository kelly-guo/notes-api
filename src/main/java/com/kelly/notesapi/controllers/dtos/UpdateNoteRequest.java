package com.kelly.notesapi.controllers.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.kelly.notesapi.entities.Priorities;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateNoteRequest {
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 1000, message = "Content cannot exceed 1000 characters")
    private String content;

    private boolean archived;

    private boolean pinned;

    private List<String>tags;

    private LocalDateTime reminder;

    private Priorities priorities;


    
}
