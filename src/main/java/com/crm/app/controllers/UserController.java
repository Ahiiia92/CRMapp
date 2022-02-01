package com.crm.app.controllers;

import com.crm.app.exceptions.NoUserException;
import com.crm.app.models.User;
import com.crm.app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping("/users")
    // public String getUsers(Model model, @AuthenticationPrincipal OAuth2User principal) {
    //    if (principal != null) {
    //        model.addAttribute("name", principal.getAttribute("name"));
    //    }
    //    return "We are logged in";
    //}

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/user")
    public ResponseEntity<Map<String, Boolean>> createUser(@Validated @RequestBody User user) {
        User userCreated = userService.createUser(user);
        Map<String, Boolean> response = new HashMap<>();
        if (userCreated == null) throw new NoUserException("The user " + user.getFirstname() + " can't be created.");

        response.put("User created", Boolean.TRUE);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Integer userId) {
        userService.deleteById(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.status(204).body(response);
    }
}
