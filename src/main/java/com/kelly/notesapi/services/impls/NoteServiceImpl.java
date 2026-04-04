package com.kelly.notesapi.services.impls;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.Tag;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.repos.NoteRepo;
import com.kelly.notesapi.repos.TagRepo;
import com.kelly.notesapi.repos.UserRepo;
import com.kelly.notesapi.services.NoteService;

public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final UserRepo userRepo;
    private final TagRepo tagRepo;
   
    public NoteServiceImpl(NoteRepo noteRepo, UserRepo userRepo, TagRepo tagRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }
    @Override
    public Note createNote(User user, String title, String content, List<String>names) {
        Note note = new Note();
        note.setTitle(title);
        note.setUser(user);
        note.setContents(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setTags(processTags(names));
        return noteRepo.save(note);


    }
    @Override
    public Page<Note> getUserNotes(User user, Boolean pinned, Boolean archived, String tag, Pageable pageable) {
        Page<Note>notes;
        Long userId = user.getUserId();
        if (pinned!=null&&archived!=null){
            notes = noteRepo.findByUserIdAndArchivedAndPinned(userId, archived, pinned,pageable);
        } else if (pinned!=null){
            notes = noteRepo.findByUserIdAndPinned(userId, pinned,pageable);
        } else if (archived!=null){
            notes = noteRepo.findByUserIdAndArchived(userId, archived,pageable);
        } else if (tag!=null){
            notes= noteRepo.findByUserIdAndTags_Name(userId, tag, pageable);
        }    
        else {
            notes = noteRepo.findByUserAndDeletedFalse(userId,pageable);
        }

        return notes;     
    }
    @Override
    public Note getByNoteId(Long id, User user) {
        Note note= noteRepo.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("User does not match");
        }
        return note;
    }
    @Override
    public Note updateNote(Long noteId, String title, String content, User user, boolean pinned, boolean archived, List<String>tags) {
        Note note= noteRepo.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("User does not match");
        }

        if (!tags.isEmpty()||tags!=null){
            note.setTags(processTags(tags));

        }
        //ADD FULL UPDATE
        note.setContents(content);
        note.setTitle(title);
        note.setUpdatedAt(LocalDateTime.now());
        note.setArchived(archived);
        note.setPinned(pinned);
        return noteRepo.save(note);
    }
    @Override
    public Set<Tag> processTags(List<String> names) {
        if (names==null||names.isEmpty()){
            return new HashSet<>();
        }
        Set<Tag>tags = new HashSet<>();
        for (String name:names){
            Tag tag = tagRepo.findByName(name).orElseGet(()->{
                Tag newTag = new Tag();
                newTag.setName(name);
                return tagRepo.save(newTag);
            });
            tags.add(tag);

        }
        return tags;


        
    }

    @Override
    public Page<Note> getTrashedNotes(Long userId, Pageable page){
        return noteRepo.findByUserIdAndDeletedTrue(userId, page);

    }

    @Override
    public void moveToTrash(Long userId, Long noteId){
        Note note = noteRepo.findByNoteIdAndUserAndDeletedFalse(noteId,userRepo.findById(userId).orElseThrow()).orElseThrow();
        note.setDeleted(true);
        noteRepo.save(note);
    }

    @Override
    public void restoreNote(Long noteId, Long userId){
        Note note = noteRepo.findById(noteId).orElseThrow();
        if (note.getUser().getUserId()!=userId){
            throw new RuntimeException("Not authorized");
        }
        if (note.isDeleted()!=true){
            throw new RuntimeException("Note not in trash");
        }
        note.setDeleted(false);
        noteRepo.save(note);
    }

    @Override
    public void permaDelete(Long noteId, Long userId){
        Note note = noteRepo.findById(noteId).orElseThrow();
        if (note.getUser().getUserId()!=userId){
            throw new RuntimeException("Not authorized");
        }
        if (note.isDeleted()!=true){
            throw new RuntimeException("Note must be in trash");
        }

        noteRepo.delete(note);
    }

    


    
}
