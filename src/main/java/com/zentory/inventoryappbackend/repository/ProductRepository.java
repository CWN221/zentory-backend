package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
