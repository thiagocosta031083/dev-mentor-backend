package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;
import com.thiagocosta.devmentor.backend.service.ProjetoPessoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projetos")
@Tag(name = "Projetos Pessoais", description = "Endpoints para gestão de projetos pessoais")
public class ProjetoPessoalController {

    @Autowired
    private ProjetoPessoalService projetoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar projeto por ID")
    @ApiResponse(responseCode = "200", description = "Projeto encontrado")
    public ResponseEntity<ProjetoPessoal> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar projetos de um usuário")
    @ApiResponse(responseCode = "200", description = "Lista de projetos")
    public ResponseEntity<List<ProjetoPessoal>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(projetoService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/em-andamento")
    @Operation(summary = "Listar projetos em andamento de um usuário")
    @ApiResponse(responseCode = "200", description = "Lista de projetos")
    public ResponseEntity<List<ProjetoPessoal>> listarEmAndamento(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(projetoService.listarEmAndamentoPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/concluidos")
    @Operation(summary = "Listar projetos concluídos de um usuário")
    @ApiResponse(responseCode = "200", description = "Lista de projetos")
    public ResponseEntity<List<ProjetoPessoal>> listarConcluidos(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(projetoService.listarConcluidosPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Criar novo projeto")
    @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso")
    public ResponseEntity<ProjetoPessoal> criar(
            @RequestParam Long usuarioId,
            @RequestParam String nome,
            @RequestParam String stack,
            @RequestParam(required = false) String proximoPasso,
            @RequestParam(required = false) Long tecnologiaId) {
        try {
            ProjetoPessoal projeto = projetoService.criar(usuarioId, nome, stack, proximoPasso, tecnologiaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar projeto")
    @ApiResponse(responseCode = "200", description = "Projeto atualizado")
    public ResponseEntity<ProjetoPessoal> atualizar(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String stack,
            @RequestParam(required = false) String proximoPasso,
            @RequestParam(required = false) Long tecnologiaId) {
        try {
            ProjetoPessoal projeto = projetoService.atualizar(id, nome, stack, proximoPasso, tecnologiaId);
            return ResponseEntity.ok(projeto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar um projeto")
    @ApiResponse(responseCode = "200", description = "Projeto iniciado")
    public ResponseEntity<ProjetoPessoal> iniciar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(projetoService.iniciar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/concluir")
    @Operation(summary = "Concluir um projeto")
    @ApiResponse(responseCode = "200", description = "Projeto concluído")
    public ResponseEntity<ProjetoPessoal> concluir(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(projetoService.concluir(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar projeto")
    @ApiResponse(responseCode = "204", description = "Projeto deletado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
