package com.crm.app.controllers;

import com.crm.app.models.User;
import com.crm.app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: CRUD for users.
    // TODO: add the User feature (service, repo etc) to enable login
    // LOGIN
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    // INDEX
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    // SHOW

    // UPDATE
    // DELETE

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
