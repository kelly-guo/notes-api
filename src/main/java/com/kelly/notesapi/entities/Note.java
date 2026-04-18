package com.kelly.notesapi.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Note {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false,updatable=false)
    private Long id;

    private String title;

    private String contents;

    private boolean pinned;

    private boolean archived;

    private boolean deleted;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime reminder;

    private boolean reminderSent;

    @OneToMany(mappedBy="note",cascade = CascadeType.ALL)
    private List<NoteShare>shares;

    @ManyToMany
    @JoinTable(name = "note_tags", joinColumns=@JoinColumn(name = "note_id"), inverseJoinColumns=@JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    
}
