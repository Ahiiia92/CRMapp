package com.crm.app.controllers;

import com.crm.app.models.User;
import com.crm.app.security.SecurityConfig;
import com.crm.app.services.UserDetailsServiceDBImpl;
import com.crm.app.services.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Users.",
        tags = {"user"})
@RestController
@CrossOrigin
@RequestMapping("/")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    private final UserService userService;
    private final SecurityConfig config;

    public UserController(
            UserService userService,
            SecurityConfig config) {
        this.userService = userService;
        this.config = config;
    }

    // INDEX
    @ApiOperation(value = "Retrieving all users", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "successful operation",
                    response = List.class
            )
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        userService.getCurrentUser();
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    // SHOW
    @ApiOperation(value = "Find user by ID", notes = "Returns a single user", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response= User.class),
            @ApiResponse(code = 404, message = "User not found") })
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(
            @ApiParam("Id of the user to get. Cannot be empty.")
            @PathVariable(name = "id") Integer userId) {
        userService.getCurrentUser();
        User profile = userService.findUserById(userId);
        return ResponseEntity.ok().body(profile);
    }

    // SIGN UP
    @ApiOperation(value = "Add a new user", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "User already exists") })
    @PostMapping("/api/auth/signup")
    public ResponseEntity<User> signUp(
            @ApiParam("User to create. Cannot be null or empty.")
            @Validated @RequestBody User user) {
        user.setPassword(config.encoder().encode(user.getPassword()));
        User newUser = userService.createUser(user);

        return ResponseEntity.status(201).body(newUser);
    }

    // LOGIN
    @ApiOperation(value = "Sign In page", tags = { "authentication" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "logging successful "),
            @ApiResponse(code = 403, message = "Unauthorized")

    })
    @PostMapping("/api/auth/signin")
    public ResponseEntity<User> signIn(
            @ApiParam("User to signin. Cqnnot be null or Empty")
            @Validated @RequestBody User user) {
        // User loggedUser = userService.findUserByUsername(user.getUsername());
        // TODO: Solve the logging feature
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UserDetailsServiceDBImpl userDetails = (UserDetailsServiceDBImpl) authentication.getPrincipal();
        return ResponseEntity.status(201).body(user);
    }

    // SIGN OUT
    @ApiOperation(value = "Sign Out Page", tags = { "authentication" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sign out successful "),
            @ApiResponse(code = 404, message = "Cannot find user"),
            @ApiResponse(code = 403, message = "Unauthorized")

    })
    @DeleteMapping("/api/auth/signout")
    public ResponseEntity<Map<String, Boolean>> signOut(
            @Validated @RequestBody User user
    ) {
        userService.removeUser(user.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok().body(response);
    }

    // UPDATE
    @PutMapping("/users/{id}")
    public ResponseEntity<User> editUser(
            @ApiParam("Id of the user to get. Cannot be empty.")
            @PathVariable(name = "id") Integer userId,
            @Validated @RequestBody User user) {
        User updatedUser = userService.editUser(user);
        return ResponseEntity.status(201).body(updatedUser);
    }
    // DELETE
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> removeUser(
            @ApiParam("Id of the user to get. Cannot be empty.")
            @RequestParam("userId") Integer userId) {
        userService.getCurrentUser();
        userService.removeUser(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("User deleted", Boolean.TRUE);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
