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

    // Repository'ler constructor injection ile enjekte ediliyor.
    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public void saveInvoice(Invoice invoice) {
        // Mevcut müşteri kontrol ediliyor, yoksa yeni oluşturuluyor.
        CustomerEntity customerEntity = customerRepository.findById(invoice.getCustomer().getTaxNumber())
                .orElseGet(() -> {
                    CustomerEntity newCustomer = new CustomerEntity();
                    newCustomer.setTaxNumber(invoice.getCustomer().getTaxNumber());
                    newCustomer.setName(invoice.getCustomer().getName());
                    return newCustomer;
                });

        customerRepository.save(customerEntity);
        
        // Fatura bilgileri Entity’ye dönüştürülüp veritabanına kaydediliyor.
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceEntity.setIssueDate(invoice.getIssueDate());
        invoiceEntity.setCustomer(customerEntity);
        invoiceEntity.setTotalAmount(invoice.getTotalAmount());
        invoiceEntity.setCurrency(invoice.getCurrency());

        invoiceRepository.save(invoiceEntity);
    }

    public List<InvoiceEntity> getAllInvoices() {
        return invoiceRepository.findAll();
    }
}
