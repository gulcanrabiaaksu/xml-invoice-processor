package com.example.invoice_processor.util;

import com.example.invoice_processor.model.Invoice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class XmlUtilTest {

    @Test
    void shouldParseInvoiceFromBase64() throws Exception {
        String base64xml = "PEludm9pY2U+CiAgICA8SW52b2ljZU51bWJlcj5JTlYtMTAwMTwvSW52b2ljZU51bWJlcj4KICAgIDxJc3N1ZURhdGU+MjAyNS0wNi0yMDwvSXNzdWVEYXRlPgogICAgPEN1c3RvbWVyPgogICAgICAgIDxOYW1lPkFjbWUgQ29ycC48L05hbWU+CiAgICAgICAgPFRheE51bWJlcj4xMjM0NTY3ODkwPC9UYXhOdW1iZXI+CiAgICA8L0N1c3RvbWVyPgogICAgPFRvdGFsQW1vdW50PjEyNTAuNTA8L1RvdGFsQW1vdW50PgogICAgPEN1cnJlbmN5PlRSWTwvQ3VycmVuY3k+CjwvSW52b2ljZT4=";

        Invoice invoice = XmlUtil.parseInvoiceFromBase64(base64xml);

        assertNotNull(invoice);
        assertEquals("INV-1001", invoice.getInvoiceNumber());
        assertEquals(LocalDate.of(2025, 6, 20), invoice.getIssueDate());
        assertEquals("Acme Corp.", invoice.getCustomer().getName());
        assertEquals("1234567890", invoice.getCustomer().getTaxNumber());
        assertEquals(new BigDecimal("1250.50"), invoice.getTotalAmount());
        assertEquals("TRY", invoice.getCurrency());
    }
}
