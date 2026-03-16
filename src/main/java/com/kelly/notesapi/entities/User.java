package com.kelly.notesapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false,updatable=false)
    private Long userId;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;
    
}
