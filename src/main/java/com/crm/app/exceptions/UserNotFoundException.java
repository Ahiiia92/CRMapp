package com.crm.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("The User with the username: " + username + " was not found.");
    }

    public  UserNotFoundException(Integer user_id) {
        super("The User with the id: " + user_id + " was not found.");
    }
}
