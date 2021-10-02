package com.crm.app.services;

import com.crm.app.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noteService")
public interface NoteService {
    List<Note> getAllNotes();
}
