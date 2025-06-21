package com.example.invoice_processor.dto;

import jakarta.validation.constraints.NotBlank;

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
