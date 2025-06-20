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

    // Getter ve Setter'lar

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
/*
CustomerEntity için taxNumber alanını @Id olarak belirledim, çünkü fatura örneğinde bu alan benzersiz ve anlamlı.

InvoiceEntity'de invoiceNumber birincil anahtar (PK).

InvoiceEntity ile CustomerEntity arasında @ManyToOne ilişkisi var; bir müşteri birçok faturaya sahip olabilir.

Entity’leri com.example.invoiceprocessor.entity paketinde tutmak düzen açısından iyi olur.*/ 