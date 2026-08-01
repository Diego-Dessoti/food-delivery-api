package com.diego.fooddeliveryapi.controller;

import com.diego.fooddeliveryapi.dto.request.CreateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.request.UpdateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.response.ProductResponseDTO;
import com.diego.fooddeliveryapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody CreateProductRequestDTO request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        ProductResponseDTO response = productService.create(request, jwt.getSubject());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequestDTO request,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(
                productService.update(id, request, jwt.getSubject())
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(
            @RequestParam(required = false) Long storeId
    ) {
        return ResponseEntity.ok(productService.findAll(storeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(productService.findById(id));
    }

}

