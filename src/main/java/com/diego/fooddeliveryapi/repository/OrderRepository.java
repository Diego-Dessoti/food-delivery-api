package com.diego.fooddeliveryapi.repository;

import com.diego.fooddeliveryapi.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"items", "store", "store.owner", "createdBy"})
    List<Order> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"items", "store", "store.owner", "createdBy"})
    List<Order> findAllByStoreOwnerEmailOrderByCreatedAtDesc(String email);

    @EntityGraph(attributePaths = {"items", "store", "store.owner", "createdBy"})
    List<Order> findAllByCreatedByEmailOrderByCreatedAtDesc(String email);

    @Override
    @EntityGraph(attributePaths = {"items", "store", "store.owner", "createdBy"})
    Optional<Order> findById(Long id);
}
