package com.diego.fooddeliveryapi.dto.response;

import com.diego.fooddeliveryapi.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        String customerName,
        AddressResponseDTO deliveryAddress,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        List<OrderItemResponseDTO> items

) {
}
