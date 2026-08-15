package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.TecnologiaRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.TecnologiaResponseDTO;
import com.thiagocosta.devmentor.backend.service.TecnologiaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tecnologias")
@Tag(name = "Tecnologias")
public class TecnologiaController {
    private final TecnologiaService service;
    public TecnologiaController(TecnologiaService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<TecnologiaResponseDTO> criar(@Valid @RequestBody TecnologiaRequestDTO request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new TecnologiaResponseDTO(service.criar(request, principal.getName())));
    }
    @GetMapping
    public List<TecnologiaResponseDTO> listar(Principal principal) {
        return service.listar(principal.getName()).stream().map(TecnologiaResponseDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public TecnologiaResponseDTO buscar(@PathVariable Long id, Principal principal) {
        return new TecnologiaResponseDTO(service.buscar(id, principal.getName()));
    }
    @PutMapping("/{id}")
    public TecnologiaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TecnologiaRequestDTO request, Principal principal) {
        return new TecnologiaResponseDTO(service.atualizar(id, request, principal.getName()));
    }
    @PutMapping("/{id}/inativar")
    public TecnologiaResponseDTO inativar(@PathVariable Long id, Principal principal) {
        return new TecnologiaResponseDTO(service.inativar(id, principal.getName()));
    }
    @PutMapping("/{id}/ativar")
    public TecnologiaResponseDTO ativar(@PathVariable Long id, Principal principal) {
        return new TecnologiaResponseDTO(service.ativar(id, principal.getName()));
    }
}
