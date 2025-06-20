package com.example.invoice_processor.repository;

import com.example.invoice_processor.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {
    // Ek sorgular gerektiğinde buraya yazabilirsin
}
 /**Her ikisi de JpaRepository<T, ID>'den extend ediyor.

ID tipi olarak entity’lerde kullandığımız birincil anahtarların tipini verdik (String).

Böylece CRUD operasyonları hazır olacak.

repository paketinde tutmak düzeni sağlar. */