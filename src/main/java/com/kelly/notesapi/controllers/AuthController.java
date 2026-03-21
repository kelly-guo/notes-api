package com.kelly.notesapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.notesapi.controllers.dtos.RegisterRequest;
import com.kelly.notesapi.controllers.dtos.UserResponse;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.mappers.UserMapper;
import com.kelly.notesapi.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        User user = userService.registerUser(registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok(userMapper.toDto(user));
    }
    
    
}
