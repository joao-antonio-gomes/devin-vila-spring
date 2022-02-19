package com.senai.vila.exception;

public class ErrorMessage {
    private int statusCode;
    private String timestamp;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, String timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}