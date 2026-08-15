package com.thiagocosta.devmentor.backend.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String m) {
        super(m);
    }
}
