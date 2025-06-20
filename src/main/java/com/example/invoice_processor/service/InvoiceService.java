package com.example.invoice_processor.service;

import com.example.invoice_processor.entity.CustomerEntity;
import com.example.invoice_processor.entity.InvoiceEntity;
import com.example.invoice_processor.model.Customer;
import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.repository.CustomerRepository;
import com.example.invoice_processor.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public void saveInvoice(Invoice invoice) {
        

        // CustomerEntity oluştur veya var olanı bul
        CustomerEntity customerEntity = customerRepository.findById(invoice.getCustomer().getTaxNumber())
                .orElseGet(() -> {
                    CustomerEntity newCustomer = new CustomerEntity();
                    newCustomer.setTaxNumber(invoice.getCustomer().getTaxNumber());
                    newCustomer.setName(invoice.getCustomer().getName());
                    return newCustomer;
                });

        // Eğer müşteri yeni ise kaydet
        customerRepository.save(customerEntity);

        // InvoiceEntity oluştur
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceEntity.setIssueDate(invoice.getIssueDate());
        invoiceEntity.setCustomer(customerEntity);
        invoiceEntity.setTotalAmount(invoice.getTotalAmount());
        invoiceEntity.setCurrency(invoice.getCurrency());

        // Kaydet
        invoiceRepository.save(invoiceEntity);
    }
}
