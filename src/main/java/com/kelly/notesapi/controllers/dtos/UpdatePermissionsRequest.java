package com.kelly.notesapi.controllers.dtos;

import lombok.Data;

@Data
public class UpdatePermissionsRequest {
    private Permissions permissions;
    private Long userId;
    
}
