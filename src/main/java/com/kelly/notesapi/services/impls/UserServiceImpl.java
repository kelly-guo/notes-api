package com.kelly.notesapi.services.impls;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.repos.UserRepo;
import com.kelly.notesapi.services.UserService;

@Service
public class UserServiceImpl implements  UserService{

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String email, String username, String password) {
        if (userRepo.findByEmail(email).isPresent()){
            throw new RuntimeException("User already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
        
    }

    @Override
    public Optional<User> findById(Long id) {
       return userRepo.findById(id);
    }
    
}
