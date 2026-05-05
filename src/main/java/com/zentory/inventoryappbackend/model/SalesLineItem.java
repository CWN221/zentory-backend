package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sales_line_items")
public class SalesLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long salesInvoiceId;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    public SalesLineItem() {}
}
