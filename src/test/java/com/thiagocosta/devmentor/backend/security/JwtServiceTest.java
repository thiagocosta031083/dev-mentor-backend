package com.thiagocosta.devmentor.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private static final String SECRET = "test-secret-key-with-at-least-thirty-two-bytes";

    @Test
    void deveGerarEValidarToken() {
        JwtService service = new JwtService(SECRET, 60000);
        String token = service.gerar("user@test.com");
        assertTrue(service.valido(token, "user@test.com"));
        assertEquals("user@test.com", service.extrairEmail(token));
    }

    @Test
    void deveRejeitarTokenExpirado() {
        JwtService service = new JwtService(SECRET, -1);
        String token = service.gerar("user@test.com");
        assertThrows(ExpiredJwtException.class, () -> service.extrairEmail(token));
    }
}
