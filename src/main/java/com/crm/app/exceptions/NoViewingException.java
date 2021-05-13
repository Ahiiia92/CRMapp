package com.crm.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoViewingException extends RuntimeException {
    public NoViewingException(String s) {
        super(s);
    }
}
