package com.kelly.notesapi.mappers;

import org.mapstruct.Mapper;

import com.kelly.notesapi.controllers.dtos.UserResponse;
import com.kelly.notesapi.entities.User;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserResponse toDto(User user);

    User fromDto(UserResponse userResponse);
    
}
