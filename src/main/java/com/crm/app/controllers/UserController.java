package com.crm.app.controllers;

import com.crm.app.models.User;
import com.crm.app.security.SecurityConfig;
import com.crm.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "Endpoints for Creating, Retrieving, Updating and Deleting of Users.",
        tags = {"user"})
@RestController
@CrossOrigin
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final SecurityConfig config;

    public UserController(
            UserService userService,
            SecurityConfig config) {
        this.userService = userService;
        this.config = config;
    }


    // TODO: CRUD for users.
    // TODO: add the User feature (service, repo etc) to enable login
    @GetMapping("/")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findUserByUsername(username);
        return ResponseEntity.ok().body(user);
    }

    // LOGIN
    @GetMapping("/login")
    public String toLogin() {
        return "login";
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
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    // SHOW
    @ApiOperation(value = "Find user by ID", notes = "Returns a single user", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response= User.class),
            @ApiResponse(code = 404, message = "User not found") })
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Integer userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User profile = userService.findUserById(userId);
        return ResponseEntity.ok().body(profile);


    }
    // TODO: Create the get user with Pathvariable being Id and return the responseEntity with the user
//

    // SIGN IN
//    @ApiOperation(value = "Add a new user", tags = { "user" })
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "User created"),
//            @ApiResponse(code = 400, message = "Invalid input"),
//            @ApiResponse(code = 409, message = "User already exists") })
//    @PostMapping("/signin")
//    TODO: Create the Post user with User as Request Body

    // UPDATE
    // DELETE

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }
}
