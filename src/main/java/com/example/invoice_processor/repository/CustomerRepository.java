package com.example.invoice_processor.repository;

import com.example.invoice_processor.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    
}
