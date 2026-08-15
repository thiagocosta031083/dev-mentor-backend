package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.LoginRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.LoginResponseDTO;
import com.thiagocosta.devmentor.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacao")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/login")
    @Operation(summary = "Autenticar e obter um token JWT")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return service.login(request);
    }
}
