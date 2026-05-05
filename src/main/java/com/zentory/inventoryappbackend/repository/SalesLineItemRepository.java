package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.SalesLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesLineItemRepository extends JpaRepository<SalesLineItem, Long> {
}
