package com.example.invoice_processor.controller;

import com.example.invoice_processor.dto.Base64XmlRequest;
import com.example.invoice_processor.entity.InvoiceEntity;
import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.service.InvoiceService;
import com.example.invoice_processor.util.XmlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Invoice API", description = "Invoice işlemleri için API")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Operation(summary = "Yeni fatura al")
    @PostMapping
    public ResponseEntity<?> receiveInvoice(@RequestBody Base64XmlRequest request) throws JAXBException {
        String base64xml = request.getBase64xml();

        if (base64xml == null || base64xml.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing 'base64xml' in request body");
        }

        Invoice invoice = XmlUtil.parseInvoiceFromBase64(base64xml);

        // Validasyon
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            throw new IllegalArgumentException("InvoiceNumber cannot be empty");
        }
        if (invoice.getCustomer() == null || invoice.getCustomer().getName() == null || invoice.getCustomer().getName().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("TotalAmount must be greater than zero");
        }

        invoiceService.saveInvoice(invoice);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Invoice saved successfully"));
    }

    @Operation(summary = "Tüm faturaları listele")
    @GetMapping
    public ResponseEntity<List<InvoiceEntity>> listInvoices() {
        List<InvoiceEntity> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }
}
