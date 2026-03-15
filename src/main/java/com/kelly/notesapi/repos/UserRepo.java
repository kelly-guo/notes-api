package com.kelly.notesapi.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelly.notesapi.entities.User;

public interface UserRepo extends JpaRepository<Long, User>{
    Optional<User>findByEmail(String email);

    
}
