package com.crm.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Throwable {
    private static final int serialVersionUID = 1;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
