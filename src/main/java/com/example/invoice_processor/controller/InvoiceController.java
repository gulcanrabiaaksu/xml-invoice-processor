package com.example.invoice_processor.controller;

import com.example.invoice_processor.dto.Base64XmlRequest;
import com.example.invoice_processor.entity.InvoiceEntity;
import com.example.invoice_processor.model.Invoice;
import com.example.invoice_processor.service.InvoiceService;
import com.example.invoice_processor.util.XmlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Fatura işlemlerine yönelik HTTP isteklerini karşılayan REST Controller.
 * Base64 olarak gönderilen XML formatındaki faturaların alınması,doğrulanması ve kaydedilmesi işlemlerini sağlar.
 */

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Invoice API", description = "Invoice işlemleri için API")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Base64 olarak gelen XML formatındaki faturayı alır, parse eder,doğrular ve kaydeder.
    @Operation(summary = "Base64 XML fatura al ve kaydet")
    @PostMapping
    public ResponseEntity<?> receiveInvoice(@RequestBody Base64XmlRequest request) throws JAXBException {
        String base64xml = request.getBase64xml();

        logger.info("Yeni fatura alma istegi alindi");

        // Base64 XML alanının boş olup olmadığını kontrol eder.
        if (base64xml == null || base64xml.trim().isEmpty()) {
            logger.warn("base64xml alani bos gonderildi");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "'base64xml' field must not be empty"));
        }

        logger.debug("Base64 XML içerigi: {}", base64xml);

        Invoice invoice;
        try {
            // Base64 XML'i parse edip Invoice modeline dönüştürür.
            invoice = XmlUtil.parseInvoiceFromBase64(base64xml);
        } catch (JAXBException e) {
            logger.error("XML parse hatasi: {}", e.getMessage());
            throw e; // GlobalExceptionHandler tarafından 400 dönülür.
        }

        // Invoice alanlarını doğrular, boşluk veya geçersiz değer varsa hata fırlatır.
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().trim().isEmpty()) {
            logger.warn("InvoiceNumber bos geldi");
            throw new IllegalArgumentException("InvoiceNumber cannot be empty"); // GlobalExceptionHandler 400 döner.
        }
        if (invoice.getCustomer() == null || invoice.getCustomer().getName() == null ||
                invoice.getCustomer().getName().trim().isEmpty()) {
            logger.warn("Customer name bos geldi");
            throw new IllegalArgumentException("Customer name cannot be empty"); // 400 döner.
        }
        if (invoice.getTotalAmount() == null || invoice.getTotalAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            logger.warn("Gecersiz totalAmount: {}", invoice.getTotalAmount());
            throw new IllegalArgumentException("TotalAmount must be greater than zero"); // 400 döner.
        }

        // Faturayı veritabanına kaydeder.
        invoiceService.saveInvoice(invoice);
        logger.info("Fatura basariyla kaydedildi: {}", invoice.getInvoiceNumber());

        // Başarılı yanıt döner.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Invoice saved successfully"));
    }

    //Sistemde kayıtlı olan tüm faturaları listeler.
    @Operation(summary = "Tüm kayıtlı faturaları getir")
    @GetMapping
    public ResponseEntity<List<InvoiceEntity>> listInvoices() {
        logger.info("Fatura listesi istegi alindi");
        List<InvoiceEntity> invoices = invoiceService.getAllInvoices();
        logger.info("Toplam {} fatura donduruldu", invoices.size());
        return ResponseEntity.ok(invoices);
    }
}
