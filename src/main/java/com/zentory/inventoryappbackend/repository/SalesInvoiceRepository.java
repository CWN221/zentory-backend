package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
    SalesInvoice findByInvoiceNumber(String invoiceNumber);
}
