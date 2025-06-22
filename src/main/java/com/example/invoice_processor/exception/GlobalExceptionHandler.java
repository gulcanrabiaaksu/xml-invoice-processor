package com.example.invoice_processor.exception;

import jakarta.xml.bind.JAXBException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Bad Request",
            "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(JAXBException.class)
    public ResponseEntity<Map<String, String>> handleJAXBException(JAXBException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "error", "Invalid XML format",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "error", "Internal server error",
                    "message", ex.getMessage() != null ? ex.getMessage() : "Unexpected error"
                ));
    }
}
