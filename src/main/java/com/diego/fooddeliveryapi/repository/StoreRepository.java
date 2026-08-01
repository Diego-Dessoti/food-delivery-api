package com.diego.fooddeliveryapi.repository;

import com.diego.fooddeliveryapi.entity.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Override
    @EntityGraph(attributePaths = "owner")
    List<Store> findAll();

    @Override
    @EntityGraph(attributePaths = "owner")
    Optional<Store> findById(Long id);

    Optional<Store> findByOwnerEmail(String email);

    boolean existsByOwnerEmail(String email);
}
