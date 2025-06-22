package com.example.invoice_processor.service;

import com.example.invoice_processor.entity.InvoiceEntity;
import com.example.invoice_processor.model.Customer;
import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.repository.CustomerRepository;
import com.example.invoice_processor.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InvoiceServiceTest {

    @Test
    void shouldSaveInvoiceSuccessfully() {
        CustomerRepository customerRepo = mock(CustomerRepository.class);
        InvoiceRepository invoiceRepo = mock(InvoiceRepository.class);

        InvoiceService invoiceService = new InvoiceService(invoiceRepo, customerRepo);

        Customer customer = new Customer();
        customer.setName("Test Ltd.");
        customer.setTaxNumber("9876543210");

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-9999");
        invoice.setIssueDate(LocalDate.of(2025, 6, 22));
        invoice.setCustomer(customer);
        invoice.setTotalAmount(new BigDecimal("1000"));
        invoice.setCurrency("TRY");

        when(customerRepo.findById("9876543210")).thenReturn(Optional.empty());

        invoiceService.saveInvoice(invoice);

        ArgumentCaptor<InvoiceEntity> invoiceCaptor = ArgumentCaptor.forClass(InvoiceEntity.class);
        verify(invoiceRepo).save(invoiceCaptor.capture());

        InvoiceEntity savedInvoice = invoiceCaptor.getValue();
        assertEquals("INV-9999", savedInvoice.getInvoiceNumber());
        assertEquals("Test Ltd.", savedInvoice.getCustomer().getName());
        assertEquals("9876543210", savedInvoice.getCustomer().getTaxNumber());
    }
}
