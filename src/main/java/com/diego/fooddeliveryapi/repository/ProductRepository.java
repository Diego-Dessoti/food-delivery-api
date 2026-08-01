package com.diego.fooddeliveryapi.repository;

import com.diego.fooddeliveryapi.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"store", "store.owner"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"store", "store.owner"})
    List<Product> findAllByStoreId(Long storeId);

    @Override
    @EntityGraph(attributePaths = {"store", "store.owner"})
    Optional<Product> findById(Long id);
}
