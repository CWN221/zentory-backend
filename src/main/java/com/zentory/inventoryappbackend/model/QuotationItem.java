package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quotation_items")
public class QuotationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quotation_id")
    private Quotation quotation;

    private String item;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    public QuotationItem() {}
}
