package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.response.DashboardResponseDTO;
import com.thiagocosta.devmentor.backend.service.EvolucaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard")
public class DashboardController {
    private final EvolucaoService service;

    public DashboardController(EvolucaoService service) {
        this.service = service;
    }

    @GetMapping("/{tecnologiaId}")
    public DashboardResponseDTO consultar(@PathVariable Long tecnologiaId, Principal principal) {
        return service.calcular(tecnologiaId, principal.getName(), LocalDate.now());
    }
}
