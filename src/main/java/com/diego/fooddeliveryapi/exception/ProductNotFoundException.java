package com.diego.fooddeliveryapi.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Produto não encontrado!");
    }
}
