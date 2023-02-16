package com.jakubowski.clinic.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .code(NOT_FOUND.value())
                .status(NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .method(httpServletRequest.getMethod())
                .build(), NOT_FOUND);
    }

    @ExceptionHandler(IllegalDateRangeExeption.class)
    public ResponseEntity<ErrorMessage> illegalDateRangeExeption(ResourceNotFoundException ex, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .timestamp(LocalDateTime.now())
                .code(NOT_ACCEPTABLE.value())
                .status(NOT_ACCEPTABLE.getReasonPhrase())
                .message(ex.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .method(httpServletRequest.getMethod())
                .build(), NOT_ACCEPTABLE);
    }
}
