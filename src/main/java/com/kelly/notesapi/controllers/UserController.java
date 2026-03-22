package com.kelly.notesapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.UserResponse;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.mappers.UserMapper;
import com.kelly.notesapi.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ResponseEntity<UserResponse> getCurrentUser(Authentication auth){
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(userMapper.toDto(user));

    }

    



    
    
}
