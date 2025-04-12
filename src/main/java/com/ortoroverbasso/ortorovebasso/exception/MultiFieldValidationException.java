package com.ortoroverbasso.ortorovebasso.exception;

import java.util.Map;

public class MultiFieldValidationException extends RuntimeException {

  private final Map<String, String> errors;

  public MultiFieldValidationException(Map<String, String> errors) {
    super("Errore di validazione multipla");
    this.errors = errors;
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
