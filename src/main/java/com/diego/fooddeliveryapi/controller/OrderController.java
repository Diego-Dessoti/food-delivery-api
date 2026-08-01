package com.diego.fooddeliveryapi.controller;

import com.diego.fooddeliveryapi.dto.request.CreateOrderRequestDTO;
import com.diego.fooddeliveryapi.dto.request.UpdateOrderRequestDTO;
import com.diego.fooddeliveryapi.dto.response.OrderResponseDTO;
import com.diego.fooddeliveryapi.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody CreateOrderRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        OrderResponseDTO responseDTO = orderService.create(
                dto,
                jwt.getSubject()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateOrderRequestDTO dto
    ) {
        OrderResponseDTO responseDTO = orderService.updateStatus(
                id,
                dto
        );

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findAll(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }
}
