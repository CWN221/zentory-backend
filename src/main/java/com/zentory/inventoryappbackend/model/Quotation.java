package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotations")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String quotationNumber;

    private String seller;
    private String sellerAddress;
    private String sellerPhone;
    private String sellerEmail;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private Double totalAmount;
    private LocalDateTime dateCreated;

    public Quotation() {}
    // getters/setters omitted for brevity
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getQuotationNumber(){return quotationNumber;} public void setQuotationNumber(String q){this.quotationNumber=q;}
}
