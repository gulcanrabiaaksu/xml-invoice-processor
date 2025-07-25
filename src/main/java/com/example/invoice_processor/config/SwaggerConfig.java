package com.example.invoice_processor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI konfigürasyon sınıfı.
 * Uygulamanın API dokümantasyonunu otomatik olarak oluşturmak için kullanılır.
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Invoice Processor API")
                .version("1.0")
                .description("API for processing invoices from XML"));
    }
}
