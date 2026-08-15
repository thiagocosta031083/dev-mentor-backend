package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.service.TecnologiaService;
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
@RequestMapping("/api/v1/tecnologias")
@Tag(name = "Tecnologias", description = "Endpoints para gestão de tecnologias e disciplinas")
public class TecnologiaController {

    @Autowired
    private TecnologiaService tecnologiaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tecnologia por ID")
    @ApiResponse(responseCode = "200", description = "Tecnologia encontrada")
    public ResponseEntity<Tecnologia> buscarPorId(@PathVariable Long id) {
        return tecnologiaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar tecnologias de um usuário")
    @ApiResponse(responseCode = "200", description = "Lista de tecnologias")
    public ResponseEntity<List<Tecnologia>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tecnologiaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/ativas")
    @Operation(summary = "Listar tecnologias ativas de um usuário")
    @ApiResponse(responseCode = "200", description = "Lista de tecnologias ativas")
    public ResponseEntity<List<Tecnologia>> listarAtivasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tecnologiaService.listarAtivasPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Criar nova tecnologia")
    @ApiResponse(responseCode = "201", description = "Tecnologia criada com sucesso")
    public ResponseEntity<Tecnologia> criar(@RequestParam Long usuarioId, @RequestParam String nome, @RequestParam(required = false) String descricao) {
        try {
            Tecnologia tecnologia = tecnologiaService.criar(usuarioId, nome, descricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(tecnologia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tecnologia")
    @ApiResponse(responseCode = "200", description = "Tecnologia atualizada")
    public ResponseEntity<Tecnologia> atualizar(@PathVariable Long id, @RequestParam String nome, @RequestParam(required = false) String descricao) {
        try {
            Tecnologia tecnologia = tecnologiaService.atualizar(id, nome, descricao);
            return ResponseEntity.ok(tecnologia);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Tecnologia ativada")
    public ResponseEntity<Tecnologia> ativar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tecnologiaService.ativar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/inativar")
    @Operation(summary = "Inativar uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Tecnologia inativada")
    public ResponseEntity<Tecnologia> inativar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tecnologiaService.inativar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tecnologia")
    @ApiResponse(responseCode = "204", description = "Tecnologia deletada")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tecnologiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
