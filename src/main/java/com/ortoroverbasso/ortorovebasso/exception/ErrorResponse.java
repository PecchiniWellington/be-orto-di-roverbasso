package com.ortoroverbasso.ortorovebasso.exception;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Response object for API errors")
public class ErrorResponse {

    @Schema(description = "Timestamp when the error occurred")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Error type", example = "Product Not Found")
    private String error;

    @Schema(description = "Error message", example = "Product not found with ID: 123")
    private String message;

    @Schema(description = "Request path where error occurred", example = "/api/products/123")
    private String path;

    @Schema(description = "Field-specific validation errors")
    private Map<String, String> fieldErrors;

    // Constructors
    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.message = message;
        this.status = status;
    }

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ErrorResponse errorResponse = new ErrorResponse();

        public Builder timestamp(LocalDateTime timestamp) {
            errorResponse.timestamp = timestamp;
            return this;
        }

        public Builder status(int status) {
            errorResponse.status = status;
            return this;
        }

        public Builder error(String error) {
            errorResponse.error = error;
            return this;
        }

        public Builder message(String message) {
            errorResponse.message = message;
            return this;
        }

        public Builder path(String path) {
            errorResponse.path = path;
            return this;
        }

        public Builder fieldErrors(Map<String, String> fieldErrors) {
            errorResponse.fieldErrors = fieldErrors;
            return this;
        }

        public ErrorResponse build() {
            if (errorResponse.timestamp == null) {
                errorResponse.timestamp = LocalDateTime.now();
            }
            return errorResponse;
        }
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", fieldErrors=" + fieldErrors +
                '}';
    }
}
