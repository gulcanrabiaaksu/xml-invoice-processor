package com.example.invoice_processor.controller;

import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.service.InvoiceService;
import com.example.invoice_processor.util.XmlUtil;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> receiveInvoice(@RequestBody Map<String, String> requestBody) throws JAXBException {
        String base64xml = requestBody.get("base64xml");
        if (base64xml == null || base64xml.isEmpty()) {
            return ResponseEntity.badRequest().body("Missing 'base64xml' in request body");
        }

        Invoice invoice = XmlUtil.parseInvoiceFromBase64(base64xml);

        // Validasyonları burada yap, hatalıysa IllegalArgumentException fırlat
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
}
