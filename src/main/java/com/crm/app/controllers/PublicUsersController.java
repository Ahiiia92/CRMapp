package com.crm.app.controllers;

import com.crm.app.models.User;
import com.crm.app.services.UserAuthenticationService;
import com.crm.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Api(
        description = "The authentication is delegated to UserAuthenticationService implementation. UserCrudService is responsible of storing the user.",
        tags = {"users"})
@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class PublicUsersController {
    @NonNull
    UserAuthenticationService authenticationService;
    @NonNull
    UserService userService;

    @ApiOperation(value = "Register a new user and return an authentication token", tags = {"users"})
    @PostMapping("/register")
    String register(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password
    ) {
        userService
                .createUser(
                        User
                        .builder()
                        .id(Integer.parseInt(username))
                        .username(username)
                        .password(password)
                        .build()
                );
        return login(username, password);
    }

    @ApiOperation(value = "login an existing user and return an authentication token (if any user found with matching password).", tags = {"users"})
    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
             @RequestParam("password") final String password) {
        return authenticationService
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("Invalid login and/or password"));
    }
}
