package com.example.invoice_processor.repository;

import com.example.invoice_processor.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, String> {

}
 