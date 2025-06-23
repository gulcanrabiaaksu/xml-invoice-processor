package com.example.invoice_processor.exception;

import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Uygulama genelinde oluşabilecek istisnaları (exception) yakalayan ve 
 * uygun HTTP yanıtları (response) dönen global hata yönetim sınıfıdır.
 */
 
@ControllerAdvice
public class GlobalExceptionHandler {
    // Logger nesnesi, hata mesajlarını loglamak için kullanılır.
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException yakalandi: {}", ex.getMessage());
        
        // Mesaj null olabilir, null ise boş string olarak ayarlanır.
        String message = ex.getMessage() == null ? "" : ex.getMessage();
        
        // HTTP 400 Bad Request döner, JSON formatında hata bilgisi içerir.
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Bad Request",
            "message", message
        ));
    }

    @ExceptionHandler(JAXBException.class)
    public ResponseEntity<Map<String, String>> handleJAXBException(JAXBException ex) {
        logger.error("JAXBException yakalandi: {}", ex.getMessage());
        
        String message = ex.getMessage() == null ? "" : ex.getMessage();
        
        // HTTP 400 Bad Request döner, XML format hatası mesajı içerir.
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "error", "Invalid XML format",
                    "message", message
                ));
    }

    //Uygulamada yakalanmayan tüm diğer beklenmeyen hataları yakalar.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        logger.error("Beklenmeyen hata: {}", ex.getMessage(), ex);
        String message = ex.getMessage() == null ? "Unexpected error" : ex.getMessage();
        
        // HTTP 500 Internal Server Error döner, hata mesajını içerir.
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "error", "Internal server error",
                    "message", message
                ));
    }
}
