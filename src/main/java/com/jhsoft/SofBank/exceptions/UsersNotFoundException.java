package com.jhsoft.SofBank.exceptions;

public class UsersNotFoundException extends RuntimeException {
    public UsersNotFoundException(String message){
        super(message);
    }
}
