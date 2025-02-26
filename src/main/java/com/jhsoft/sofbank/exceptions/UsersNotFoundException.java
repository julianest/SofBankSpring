package com.jhsoft.sofbank.exceptions;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(String message){
        super(message);
    }
}
