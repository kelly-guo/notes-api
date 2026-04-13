package com.kelly.notesapi.controllers.dtos;

import java.time.LocalDateTime;

import com.kelly.notesapi.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {

    private Long id;
    private User user;
    private boolean read;
    private LocalDateTime createdAt;
    
}
