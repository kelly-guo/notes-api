package com.kelly.notesapi.mappers;

import org.mapstruct.Mapper;

import com.kelly.notesapi.controllers.dtos.NoteResponse;
import com.kelly.notesapi.entities.Note;

@Mapper(componentModel="spring")
public interface NoteMapper {

    NoteResponse toDto(Note note);

    Note fromDto(NoteResponse noteResponse);

    
    
    
}
