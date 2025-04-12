package com.ortoroverbasso.ortorovebasso.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ortoroverbasso.ortorovebasso.exception.MultiFieldValidationException;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(MultiFieldValidationException.class)
  public ResponseEntity<Map<String, Map<String, String>>> handleMultiple(MultiFieldValidationException ex) {
    return new ResponseEntity<>(
        Map.of("errors", ex.getErrors()),
        HttpStatus.BAD_REQUEST);
  }
}
