package com.ortoroverbasso.ortorovebasso.dto;

public class GenericResponseDto {

    private int status;
    private String message;

    // Costruttore
    public GenericResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getter e setter
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
