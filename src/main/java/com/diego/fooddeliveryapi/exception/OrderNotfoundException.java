package com.diego.fooddeliveryapi.exception;

public class OrderNotfoundException extends RuntimeException {

    public OrderNotfoundException() {
        super("Pedido não encontrado");
    }
}
