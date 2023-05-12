package com.example.demo.blogapplication.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp:", LocalDateTime.now());
        body.put("message:", "No data found");
        body.put("description:", request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                              WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp:", LocalDateTime.now());
        body.put("message:", ex.getMessage());
        body.put("description:", request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<Object> blogNotFoundException(
            BlogAPIException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp:", LocalDateTime.now());
        body.put("message:", ex.getMessage());
        body.put("description:", request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalException(
            Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp:", LocalDateTime.now());
        body.put("message:", ex.getMessage());
        body.put("description:", request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


//    @ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class, UnsupportedJwtException.class,
//            IllegalArgumentException.class})
//    public ResponseEntity<Object> handleJwtExceptions(Exception ex,WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp:", LocalDateTime.now());
//        body.put("message:", ex.getMessage());
//        body.put("description:", request.getDescription(false));
//
//
//        if (ex instanceof MalformedJwtException) {
//            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//        } else if (ex instanceof ExpiredJwtException) {
//            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//        } else if (ex instanceof UnsupportedJwtException) {
//            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//        } else if (ex instanceof IllegalArgumentException) {
//            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//        } else {
//            // Handle any other exceptions not explicitly caught above
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
//        }
//    }


}