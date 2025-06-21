package com.example.invoice_processor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "tax_number", nullable = false, unique = true)
    private String taxNumber;

    @Column(name = "name", nullable = false)
    private String name;

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
