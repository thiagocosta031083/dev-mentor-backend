package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.ProjetoPessoalRequestDTO;
import com.thiagocosta.devmentor.backend.dto.response.ProjetoPessoalResponseDTO;
import com.thiagocosta.devmentor.backend.service.ProjetoPessoalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projetos")
@Tag(name = "Projetos pessoais")
public class ProjetoPessoalController {
    private final ProjetoPessoalService service;
    public ProjetoPessoalController(ProjetoPessoalService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProjetoPessoalResponseDTO> criar(@Valid @RequestBody ProjetoPessoalRequestDTO request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProjetoPessoalResponseDTO(service.criar(request, principal.getName())));
    }
    @GetMapping
    public List<ProjetoPessoalResponseDTO> listar(Principal principal) {
        return service.listar(principal.getName()).stream().map(ProjetoPessoalResponseDTO::new).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ProjetoPessoalResponseDTO buscar(@PathVariable Long id, Principal principal) {
        return new ProjetoPessoalResponseDTO(service.buscar(id, principal.getName()));
    }
    @PutMapping("/{id}")
    public ProjetoPessoalResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ProjetoPessoalRequestDTO request, Principal principal) {
        return new ProjetoPessoalResponseDTO(service.atualizar(id, request, principal.getName()));
    }
}
