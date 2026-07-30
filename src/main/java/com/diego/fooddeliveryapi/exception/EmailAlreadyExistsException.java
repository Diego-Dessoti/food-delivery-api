package com.diego.fooddeliveryapi.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(){
        super("Email já existe em nossa base!");
    }
}
