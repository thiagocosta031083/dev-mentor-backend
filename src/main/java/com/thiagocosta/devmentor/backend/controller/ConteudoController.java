package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.dto.request.*;
import com.thiagocosta.devmentor.backend.dto.response.ConteudoResponseDTO;
import com.thiagocosta.devmentor.backend.service.ConteudoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/conteudos")
@Tag(name = "Conteudos planejados")
public class ConteudoController {
    private final ConteudoService service;
    public ConteudoController(ConteudoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ConteudoResponseDTO> criar(@Valid @RequestBody ConteudoRequestDTO request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ConteudoResponseDTO(service.criar(request, principal.getName())));
    }
    @GetMapping("/{id}")
    public ConteudoResponseDTO buscar(@PathVariable Long id, Principal principal) {
        return new ConteudoResponseDTO(service.buscar(id, principal.getName()));
    }
    @GetMapping("/tecnologia/{tecnologiaId}")
    public List<ConteudoResponseDTO> listar(@PathVariable Long tecnologiaId, Principal principal) {
        return service.listar(tecnologiaId, principal.getName()).stream().map(ConteudoResponseDTO::new).collect(Collectors.toList());
    }
    @PutMapping("/{id}")
    public ConteudoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ConteudoRequestDTO request, Principal principal) {
        return new ConteudoResponseDTO(service.atualizar(id, request, principal.getName()));
    }
    @PutMapping("/{id}/iniciar")
    public ConteudoResponseDTO iniciar(@PathVariable Long id, Principal principal) {
        return new ConteudoResponseDTO(service.iniciar(id, principal.getName()));
    }
    @PutMapping("/{id}/concluir")
    public ConteudoResponseDTO concluir(@PathVariable Long id, @Valid @RequestBody ConcluirConteudoRequestDTO request, Principal principal) {
        return new ConteudoResponseDTO(service.concluir(id, request.getNivelDominio(), principal.getName()));
    }
}
