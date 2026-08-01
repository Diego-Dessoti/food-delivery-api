package com.diego.fooddeliveryapi.exception;

import com.diego.fooddeliveryapi.enums.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {

    public InvalidOrderStatusTransitionException(OrderStatus currentStatus, OrderStatus newStatus) {
        super("Não é permitido alterar o status de "
                + currentStatus
                + " para "
                + newStatus
        );
    }
}
