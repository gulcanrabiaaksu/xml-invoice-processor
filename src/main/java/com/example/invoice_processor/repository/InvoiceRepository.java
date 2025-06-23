package com.example.invoice_processor.repository;

import com.example.invoice_processor.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Fatura bilgilerini veritabanında tutmak için kullanılan JPA repository arayüzüdür.
 * JpaRepository<InvoiceEntity, String> interface'ini genişleterek CRUD işlemlerini sağlar.
 * Birincil anahtar (ID) tipi String (invoiceNumber) olarak belirtilmiştir.
 */

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

}
 