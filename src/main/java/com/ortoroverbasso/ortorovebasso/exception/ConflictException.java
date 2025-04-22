package com.ortoroverbasso.ortorovebasso.exception;

public class ConflictException extends RuntimeException {

    // Costruttore con messaggio
    public ConflictException(String message) {
        super(message);
    }

    // Costruttore con messaggio e causa
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
