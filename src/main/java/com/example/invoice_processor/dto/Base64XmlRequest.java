package com.example.invoice_processor.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * API'ye gönderilen isteklerde kullanılan DTO (Data Transfer Object).
 * Base64 formatında XML içeriğini taşır.
 */

public class Base64XmlRequest {

    @NotBlank
    private String base64xml;

    public String getBase64xml() {
        return base64xml;
    }

    public void setBase64xml(String base64xml) {
        this.base64xml = base64xml;
    }
}
