package com.thiagocosta.devmentor.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String m) {
        super(m);
    }
}
