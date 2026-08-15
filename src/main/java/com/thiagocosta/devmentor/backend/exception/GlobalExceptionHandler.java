package com.thiagocosta.devmentor.backend.exception;

import com.thiagocosta.devmentor.backend.dto.response.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFound(ResourceNotFoundException e, HttpServletRequest r) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", e.getMessage(), r, null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> conflict(ConflictException e, HttpServletRequest r) {
        return build(HttpStatus.CONFLICT, "CONFLICT", e.getMessage(), r, null);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> business(BusinessException e, HttpServletRequest r) {
        return build(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR", e.getMessage(), r, null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> credentials(BadCredentialsException e, HttpServletRequest r) {
        return build(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "E-mail ou senha inválidos", r, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> validation(MethodArgumentNotValidException e, HttpServletRequest r) {
        Map<String, String> fields = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> fields.put(f.getField(), f.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Dados inválidos", r, fields);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> malformed(HttpMessageNotReadableException e, HttpServletRequest r) {
        return build(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "JSON ausente, malformado ou com valor inválido", r, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> integrity(DataIntegrityViolationException e, HttpServletRequest r) {
        return build(HttpStatus.CONFLICT, "DATA_CONFLICT", "A operação viola uma restrição de dados", r, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> unexpected(Exception e, HttpServletRequest r) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "Erro interno inesperado", r, null);
    }

    private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String error, String message,
                                                    HttpServletRequest request, Map<String, String> fields) {
        return ResponseEntity.status(status)
                .body(new ErrorResponseDTO(status.value(), error, message, request.getRequestURI(), fields));
    }
}
