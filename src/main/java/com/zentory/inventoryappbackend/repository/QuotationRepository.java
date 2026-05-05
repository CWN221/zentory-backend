package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    Quotation findByQuotationNumber(String quotationNumber);
}
