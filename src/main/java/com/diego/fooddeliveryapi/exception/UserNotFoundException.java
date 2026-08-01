package com.diego.fooddeliveryapi.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Usuário não autenticado!");
    }
}
