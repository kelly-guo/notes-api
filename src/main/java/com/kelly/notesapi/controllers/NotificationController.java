package com.kelly.notesapi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.NotificationResponse;
import com.kelly.notesapi.mappers.NotificationMapper;
import com.kelly.notesapi.services.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping
    public List<NotificationResponse> getNotifications(@RequestParam Long userId){
        return notificationService.getUserNotifs(userId).stream().map(notificationMapper::toDto).toList();
    }

    @GetMapping
    public List<NotificationResponse> getUnreadNotifications(@RequestParam Long userId){
        return notificationService.getUnreadNotifs(userId).stream().map(notificationMapper::toDto).toList();
    }

    @PatchMapping(path = "/{notifId}/read")
    public void markOneAsRead (@PathVariable Long notifId){
        notificationService.markAsRead(notifId);
    }

    @PatchMapping(path = "/read-all")
    public void markAllAsRead (@RequestParam Long userId){
        notificationService.markAllAsRead(userId);
    }





    
}
