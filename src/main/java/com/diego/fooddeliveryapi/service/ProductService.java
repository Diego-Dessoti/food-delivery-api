package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.CreateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.request.UpdateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.response.ProductResponseDTO;
import com.diego.fooddeliveryapi.entity.Product;
import com.diego.fooddeliveryapi.exception.ProductNotFoundException;
import com.diego.fooddeliveryapi.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO request) {
        Product product = new Product();

        product.setName(request.name().trim());
        product.setDescription(normalizeDescription(request.description()));
        product.setPrice(request.price());
        product.setActive(true);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDTO update(
            Long id,
            UpdateProductRequestDTO request
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.setName(request.name().trim());
        product.setDescription(normalizeDescription(request.description()));
        product.setPrice(request.price());
        product.setActive(request.active());

        return toResponse(product);
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        return toResponse(product);
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        return description.trim();
    }

    private ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive()
        );
    }
}
