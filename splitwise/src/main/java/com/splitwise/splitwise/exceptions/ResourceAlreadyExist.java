package com.splitwise.splitwise.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExist extends ApiException {
    public ResourceAlreadyExist(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
