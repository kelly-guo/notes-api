package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    

}
