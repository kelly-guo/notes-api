package com.kelly.notesapi.services.impls;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kelly.notesapi.controllers.dtos.Permissions;
import com.kelly.notesapi.controllers.dtos.SharedNoteResponse;
import com.kelly.notesapi.controllers.dtos.UpdatePermissionsRequest;
import com.kelly.notesapi.entities.Note;
import com.kelly.notesapi.entities.NoteShare;
import com.kelly.notesapi.entities.Tag;
import com.kelly.notesapi.entities.User;
import com.kelly.notesapi.repos.NoteRepo;
import com.kelly.notesapi.repos.NoteShareRepo;
import com.kelly.notesapi.repos.TagRepo;
import com.kelly.notesapi.repos.UserRepo;
import com.kelly.notesapi.services.NoteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepo noteRepo;
    private final UserRepo userRepo;
    private final TagRepo tagRepo;
    private final NoteShareRepo noteShareRepo;
   
    
    @Override
    public Note createNote(User user, String title, String content, List<String>names, LocalDateTime reminder) {
        Note note = new Note();
        note.setTitle(title);
        note.setUser(user);
        note.setContents(content);
        note.setCreatedAt(LocalDateTime.now());
        note.setTags(processTags(names));
        note.setReminder(reminder);
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
    public Note updateNote(Long noteId, String title, String content, User user, boolean pinned, boolean archived, List<String>tags,LocalDateTime reminder) {
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
        note.setReminder(reminder);
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
    @Override
    public void removeReminder(Long noteId, Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        Note note= noteRepo.findById(noteId).orElseThrow();
        note.setReminder(null);
    }
    @Override
    public Page<Note> getUpcomingReminderNotes(Long userId, Pageable page) {
        User user = userRepo.findById(userId).orElseThrow();
        return noteRepo.findByUserAndDeletedFalseAndReminderAtAfter(user, LocalDateTime.now(), page);
    }
    @Override
    public Page<Note> getReminderNotes(Long userId, Pageable page) {
        User user = userRepo.findById(userId).orElseThrow();
        return noteRepo.findByUserAndDeletedFalseAndReminderAtIsNotNull(user, page);
    }
    @Override
    public void shareNote(Long userId, Long noteId, Permissions permissions) {
        Note note = noteRepo.findById(noteId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();

        NoteShare noteShare = new NoteShare();
        noteShare.setNote(note);
        noteShare.setUser(user);
        noteShare.setPermissions(permissions);
        noteShareRepo.save(noteShare);
        
    }
    @Override
    public Page<Note> getSharedNotes(User user, Pageable pageable) {
        return noteShareRepo.findByUser(user,pageable).map(NoteShare::getNote);
    }
    @Override
    public boolean canEdit(User user, Note note) {
        if (note.getUser().equals(user)) return true;
        return noteShareRepo.findByNoteAndUser(note,user).map(share->share.getPermissions()==Permissions.WRITE).orElse(false);
    }
    @Override
    public void removeAccess(Long noteId, Long userId, User currentUser) {
        Note note = noteRepo.findById(noteId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        if (!note.getUser().equals(currentUser)) throw new RuntimeException("You are not the owner!");
        NoteShare noteShare = noteShareRepo.findByNoteAndUser(note,user).orElseThrow();
        noteShareRepo.delete(noteShare);
    }
    @Override
    public void editPermissions(UpdatePermissionsRequest updatePermissionsRequest, User currentUser, Long noteId) {
        Note note = noteRepo.findById(noteId).orElseThrow();
        if (!note.getUser().equals(currentUser)) throw new RuntimeException("You are not the owner!");
        User user = userRepo.findById(updatePermissionsRequest.getUserId()).orElseThrow();
        NoteShare noteShare = noteShareRepo.findByNoteAndUser(note,user).orElseThrow();
        noteShare.setPermissions(updatePermissionsRequest.getPermissions());
        noteShareRepo.save(noteShare);

    }
    @Override
    public List<SharedNoteResponse> getShares(Long noteId, User currentUser) {
        Note note=noteRepo.findById(noteId).orElseThrow();
        if (!note.getUser().equals(currentUser)) throw new RuntimeException("You are not the owner!");
        return noteShareRepo.findByNote(note).stream().map(share ->{
            SharedNoteResponse sharedNoteResponse = new SharedNoteResponse();
            sharedNoteResponse.setEmail(share.getUser().getEmail());
            sharedNoteResponse.setUserId(share.getUser().getUserId());
            sharedNoteResponse.setPermissions(share.getPermissions());
            return sharedNoteResponse;
        }).toList();
        
    }
    @Override
    public void shareNoteByEmail(User currentUser, Long noteId, String email, Permissions permissions) {
        Note note=noteRepo.findById(noteId).orElseThrow();
        if (!note.getUser().equals(currentUser)) throw new RuntimeException("You are not the owner!");
        User user = userRepo.findByEmail(email).orElseThrow();
        if (note.getUser().equals(user)) throw new RuntimeException("Cannot share with yourself!");
        if (noteShareRepo.findByNoteAndUser(note,user).isPresent()) throw new RuntimeException("Already shared!");
        NoteShare share= new NoteShare();
        share.setNote(note);
        share.setPermissions(permissions);
        share.setUser(user);

        noteShareRepo.save(share);
        
    }
    @Override
    public void checkAccess(User user, Note note) {
        if (note.getUser().equals(user)) return;

        NoteShare noteShare = noteShareRepo.findByNoteAndUser(note,user).orElseThrow();

        if (noteShare.getPermissions().equals(Permissions.READ)) throw new RuntimeException("No permission to write!");
    }

    

    


    
}
