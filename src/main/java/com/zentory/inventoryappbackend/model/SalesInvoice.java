package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_invoices")
public class SalesInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    private String customerName;

    private Double totalAmount;

    private Double totalCogs;

    private Double grossMargin;

    private LocalDateTime createdAt;

    public SalesInvoice() {}
    // getters/setters
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getInvoiceNumber(){return invoiceNumber;} public void setInvoiceNumber(String n){this.invoiceNumber=n;}
    public String getCustomerName(){return customerName;} public void setCustomerName(String c){this.customerName=c;}
    public Double getTotalAmount(){return totalAmount;} public void setTotalAmount(Double t){this.totalAmount=t;}
    public Double getTotalCogs(){return totalCogs;} public void setTotalCogs(Double t){this.totalCogs=t;}
    public Double getGrossMargin(){return grossMargin;} public void setGrossMargin(Double g){this.grossMargin=g;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime dt){this.createdAt=dt;}
}
