package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotesService {
    @Autowired
    NotesMapper notesMapper;

    @Autowired
    AuthService authService;

    public Integer save(Notes note){
        Users currentUser = authService.getCurrentUser();
        note.setUser(currentUser);
        return notesMapper.save(note);
    }

    public Integer update(Notes note){
        return notesMapper.update(note);
    }

    public void delete(Notes note){
        notesMapper.delete(note);
    }

    public Integer create(Notes note){
        return notesMapper.save(note);
    }

    public Notes findById(Integer id){
        return notesMapper.findById(id);
    }

    public List<Notes> findAllByUser(Users user){
        System.out.println("finding notes for user" + user);
        return notesMapper.findAllByUser(user);
    }
}
