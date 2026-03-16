package com.kelly.notesapi.services;

import java.util.Optional;

import com.kelly.notesapi.entities.User;



public interface UserService {

    User registerUser(String email, String username, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);



}