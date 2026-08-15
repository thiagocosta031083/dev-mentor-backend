package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.PlanoEstudoRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.PlanoEstudoResponseDTO;
import com.thiagocosta.devmentor.backend.service.PlanoEstudoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/planos")
@Tag(name = "Planos de estudo")
public class PlanoEstudoController {
    private final PlanoEstudoService service;
    public PlanoEstudoController(PlanoEstudoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<PlanoEstudoResponseDTO> criar(@Valid @RequestBody PlanoEstudoRequestDTO request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new PlanoEstudoResponseDTO(service.criar(request, principal.getName())));
    }
    @GetMapping("/{id}")
    public PlanoEstudoResponseDTO buscar(@PathVariable Long id, Principal principal) {
        return new PlanoEstudoResponseDTO(service.buscar(id, principal.getName()));
    }
    @GetMapping("/tecnologia/{tecnologiaId}")
    public List<PlanoEstudoResponseDTO> listar(@PathVariable Long tecnologiaId, Principal principal) {
        return service.listar(tecnologiaId, principal.getName()).stream().map(PlanoEstudoResponseDTO::new).collect(Collectors.toList());
    }
    @PutMapping("/{id}")
    public PlanoEstudoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PlanoEstudoRequestDTO request, Principal principal) {
        return new PlanoEstudoResponseDTO(service.atualizar(id, request, principal.getName()));
    }
}
