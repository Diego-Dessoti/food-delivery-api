package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.request.CreateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.request.UpdateProductRequestDTO;
import com.diego.fooddeliveryapi.dto.response.ProductResponseDTO;
import com.diego.fooddeliveryapi.entity.Product;
import com.diego.fooddeliveryapi.entity.Store;
import com.diego.fooddeliveryapi.exception.ProductNotFoundException;
import com.diego.fooddeliveryapi.exception.StoreNotFoundException;
import com.diego.fooddeliveryapi.repository.ProductRepository;
import com.diego.fooddeliveryapi.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductService(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO request, String authenticatedEmail) {
        Store store = storeRepository.findByOwnerEmail(authenticatedEmail)
                .orElseThrow(StoreNotFoundException::new);

        Product product = new Product();

        product.setName(request.name().trim());
        product.setDescription(normalizeDescription(request.description()));
        product.setPrice(request.price());
        product.setActive(true);
        product.setStore(store);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Transactional
    public ProductResponseDTO update(
            Long id,
            UpdateProductRequestDTO request,
            String authenticatedEmail
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if (!product.getStore().getOwner().getEmail().equals(authenticatedEmail)) {
            throw new ProductNotFoundException();
        }

        product.setName(request.name().trim());
        product.setDescription(normalizeDescription(request.description()));
        product.setPrice(request.price());
        product.setActive(request.active());

        return toResponse(product);
    }

    public List<ProductResponseDTO> findAll(Long storeId) {
        if (storeId == null) {
            return productRepository.findAll()
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        if (!storeRepository.existsById(storeId)) {
            throw new StoreNotFoundException();
        }

        return productRepository.findAllByStoreId(storeId)
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

    public ProductResponseDTO toResponse(Product product) {
        Store store = product.getStore();

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getActive(),
                store.getId(),
                store.getName()
        );
    }
}
