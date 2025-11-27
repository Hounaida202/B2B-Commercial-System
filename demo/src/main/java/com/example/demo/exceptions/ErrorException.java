package com.example.demo.exceptions;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorException {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorException(String message, String error, int status, String path) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.error = error;
        this.status = status;
        this.path = path;
    }
}
