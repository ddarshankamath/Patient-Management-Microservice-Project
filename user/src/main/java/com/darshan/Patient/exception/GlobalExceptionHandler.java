package com.darshan.Patient.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlredyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlredyExistException(EmailAlredyExistsException ex) {
        log.warn("email exists{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email Address Alredy Exixts");

        return ResponseEntity.ok().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {
        log.warn("patient not found", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "patient not found");

        return ResponseEntity.badRequest().body(errors);

    }

}
