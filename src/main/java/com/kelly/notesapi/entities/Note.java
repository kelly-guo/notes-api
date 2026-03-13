package com.kelly.notesapi.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private Long id;

    private String title;

    private String contents;

    private boolean pinned;

    private boolean archived;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    
}
