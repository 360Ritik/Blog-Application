package com.example.demo.blogapplication.exception;


import javax.naming.AuthenticationException;

public class JwtIncorrectException extends AuthenticationException {
    public JwtIncorrectException(String message){
        super(message);
    }
}
