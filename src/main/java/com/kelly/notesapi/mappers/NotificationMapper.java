package com.kelly.notesapi.mappers;

import org.mapstruct.Mapper;

import com.kelly.notesapi.controllers.dtos.NotificationResponse;
import com.kelly.notesapi.entities.Notification;

@Mapper(componentModel="spring")
public interface NotificationMapper {

    NotificationResponse toDto(Notification notification);
    
}
