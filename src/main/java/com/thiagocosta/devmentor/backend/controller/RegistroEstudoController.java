package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.RegistroEstudoRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.RegistroEstudoResponseDTO;
import com.thiagocosta.devmentor.backend.service.RegistroEstudoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registros")
@Tag(name = "Registros de estudo")
public class RegistroEstudoController {
    private final RegistroEstudoService service;
    public RegistroEstudoController(RegistroEstudoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<RegistroEstudoResponseDTO> criar(@Valid @RequestBody RegistroEstudoRequestDTO request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegistroEstudoResponseDTO(service.criar(request, principal.getName())));
    }
    @GetMapping("/{id}")
    public RegistroEstudoResponseDTO buscar(@PathVariable Long id, Principal principal) {
        return new RegistroEstudoResponseDTO(service.buscar(id, principal.getName()));
    }
    @GetMapping("/tecnologia/{tecnologiaId}")
    public List<RegistroEstudoResponseDTO> listar(@PathVariable Long tecnologiaId, Principal principal) {
        return service.listar(tecnologiaId, principal.getName()).stream().map(RegistroEstudoResponseDTO::new).collect(Collectors.toList());
    }
}
