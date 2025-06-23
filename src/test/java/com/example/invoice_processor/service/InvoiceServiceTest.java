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

//InvoiceService sınıfının birim testleri.
public class InvoiceServiceTest {

    @Test
    void shouldSaveInvoiceSuccessfully() {
        // Mock nesneler oluşturuluyor.
        CustomerRepository customerRepo = mock(CustomerRepository.class);
        InvoiceRepository invoiceRepo = mock(InvoiceRepository.class);

        // Test edilecek service nesnesi mock repository'lerle oluşturuluyor.
        InvoiceService invoiceService = new InvoiceService(invoiceRepo, customerRepo);

        // Test için müşteri objesi hazırlanıyor.
        Customer customer = new Customer();
        customer.setName("Test Ltd.");
        customer.setTaxNumber("9876543210");

        // Test için fatura objesi hazırlanıyor.
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-9999");
        invoice.setIssueDate(LocalDate.of(2025, 6, 22));
        invoice.setCustomer(customer);
        invoice.setTotalAmount(new BigDecimal("1000"));
        invoice.setCurrency("TRY");

         // CustomerRepository findById çağrısına Optional.empty() döndürülmesi simüle ediliyor.
        when(customerRepo.findById("9876543210")).thenReturn(Optional.empty());

        // saveInvoice metodu çağrılıyor (test edilen metod).
        invoiceService.saveInvoice(invoice);

        // invoiceRepo.save metoduna geçirilen InvoiceEntity'nin yakalanması.
        ArgumentCaptor<InvoiceEntity> invoiceCaptor = ArgumentCaptor.forClass(InvoiceEntity.class);
        verify(invoiceRepo).save(invoiceCaptor.capture());

        // Yakalanan entity üzerinde doğrulamalar yapılıyor.
        InvoiceEntity savedInvoice = invoiceCaptor.getValue();
        assertEquals("INV-9999", savedInvoice.getInvoiceNumber());
        assertEquals("Test Ltd.", savedInvoice.getCustomer().getName());
        assertEquals("9876543210", savedInvoice.getCustomer().getTaxNumber());
    }
}
