package com.example.invoice_processor.repository;

import com.example.invoice_processor.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Müşteri bilgilerini veritabanında tutmak için kullanılan JPA repository arayüzüdür.
 * JpaRepository<CustomerEntity, String> interface'ini genişleterek CRUD işlemlerini sağlar.
 * Birincil anahtar (ID) tipi String (taxNumber) olarak belirtilmiştir.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    
}
