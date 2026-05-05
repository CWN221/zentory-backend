package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {
}
