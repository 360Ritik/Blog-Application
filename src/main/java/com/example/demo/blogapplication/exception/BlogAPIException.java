package com.example.demo.blogapplication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class BlogAPIException extends RuntimeException {

    @Getter
    private final HttpStatus status;
    private final String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}