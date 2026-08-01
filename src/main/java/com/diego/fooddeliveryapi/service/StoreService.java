package com.diego.fooddeliveryapi.service;

import com.diego.fooddeliveryapi.dto.response.StoreResponseDTO;
import com.diego.fooddeliveryapi.entity.Store;
import com.diego.fooddeliveryapi.exception.StoreNotFoundException;
import com.diego.fooddeliveryapi.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreResponseDTO> findAll() {
        return storeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StoreResponseDTO findById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(StoreNotFoundException::new);

        return toResponse(store);
    }

    public StoreResponseDTO toResponse(Store store) {
        return new StoreResponseDTO(
                store.getId(),
                store.getName(),
                store.isActive(),
                store.getOwner().getId(),
                store.getOwner().getName()
        );
    }
}
