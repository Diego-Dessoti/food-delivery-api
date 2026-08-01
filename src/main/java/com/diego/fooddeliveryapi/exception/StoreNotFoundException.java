package com.diego.fooddeliveryapi.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException() {
        super("Loja não encontrada");
    }
}
