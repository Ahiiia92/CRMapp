package com.crm.app.controllers;

import com.crm.app.models.Note;
import com.crm.app.services.NoteService;
import com.crm.app.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Endpoints for creating, retrieving, updating and deleting Notes.", tags = {"notes"})
@RestController
@CrossOrigin
@RequestMapping("api/v1/contacts/{id}/viewings/{viewingsId}/")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    // TODO: Need to be tested as SHOW method is missing for Viewing
    @GetMapping("")
    public ResponseEntity<List<Note>> getNotes() {
        userService.getCurrentUser();
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok().body(notes);
    }
}
