package com.example.invoice_processor.service;

import com.example.invoice_processor.entity.CustomerEntity;
import com.example.invoice_processor.entity.InvoiceEntity;
import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.repository.CustomerRepository;
import com.example.invoice_processor.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public void saveInvoice(Invoice invoice) {
        CustomerEntity customerEntity = customerRepository.findById(invoice.getCustomer().getTaxNumber())
                .orElseGet(() -> {
                    CustomerEntity newCustomer = new CustomerEntity();
                    newCustomer.setTaxNumber(invoice.getCustomer().getTaxNumber());
                    newCustomer.setName(invoice.getCustomer().getName());
                    return newCustomer;
                });

        // Customer'ı veritabanına kaydet (eğer yeni ise)
        customerRepository.save(customerEntity);

        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceEntity.setIssueDate(invoice.getIssueDate());
        invoiceEntity.setCustomer(customerEntity);
        invoiceEntity.setTotalAmount(invoice.getTotalAmount());
        invoiceEntity.setCurrency(invoice.getCurrency());

        // Faturayı kaydet
        invoiceRepository.save(invoiceEntity);
    }

    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
