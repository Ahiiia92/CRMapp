package com.crm.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserException extends RuntimeException {
    public NoUserException(String s) {
        super(s);
    }
    public NoUserException(Integer id) {
        super("The User with the id: " + id + " was not found.");
    }
}
