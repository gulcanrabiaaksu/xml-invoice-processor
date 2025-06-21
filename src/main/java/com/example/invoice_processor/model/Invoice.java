package com.example.invoice_processor.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "Invoice")
@XmlAccessorType(XmlAccessType.FIELD) 
public class Invoice {

    @XmlElement(name = "InvoiceNumber")
    private String invoiceNumber;
    
    @XmlElement(name = "IssueDate")
    private String issueDateString;

    @XmlElement(name = "Customer")
    private Customer customer;

    @XmlElement(name = "TotalAmount")
    private BigDecimal totalAmount;

    @XmlElement(name = "Currency")
    private String currency;

    public Invoice() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getIssueDate() {
        if (issueDateString == null) return null;
        return LocalDate.parse(issueDateString);
    }

    public void setIssueDateString(String issueDateString) {
        this.issueDateString = issueDateString;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    
}
