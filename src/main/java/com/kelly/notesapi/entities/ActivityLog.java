package com.kelly.notesapi.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLog {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name ="note_id",nullable=false)
    private Note note;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String details;

    private LocalDateTime timeStamp;
    
}
