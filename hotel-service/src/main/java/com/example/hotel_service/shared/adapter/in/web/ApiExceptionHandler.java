package com.example.hotel_service.shared.adapter.in.web;

import com.example.hotel_service.feature.hotel.adapter.in.web.UnauthorizedException;
import com.example.hotel_service.shared.domain.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<String> notFound(ResourceNotFoundException exception) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()); }
    
    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<Void> unauthorized() { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
}
