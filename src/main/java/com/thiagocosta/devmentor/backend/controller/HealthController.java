package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.response.HealthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Verifica a disponibilidade da API")
public class HealthController {

    @GetMapping
    @Operation(summary = "Consultar a saude da API")
    @ApiResponse(responseCode = "200", description = "API disponivel")
    public HealthResponseDTO health() {
        return new HealthResponseDTO("UP", "Dev Mentor Backend");
    }
}
