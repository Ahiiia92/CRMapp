package com.crm.app.services;

import com.crm.app.models.Note;
import com.crm.app.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteServiceDBImpl implements NoteService {
    @Autowired
    private NoteRepository repo;

    @Override
    public List<Note> getAllNotes() {
        return repo.findAll();
    }
}
