package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import com.thiagocosta.devmentor.backend.service.PlanejamentoEstudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/planejamentos")
@Tag(name = "Planejamentos de Estudo", description = "Endpoints para gestão de planejamentos de horas de estudo")
public class PlanejamentoEstudoController {

    @Autowired
    private PlanejamentoEstudoService planejamentoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar planejamento por ID")
    @ApiResponse(responseCode = "200", description = "Planejamento encontrado")
    public ResponseEntity<PlanejamentoEstudo> buscarPorId(@PathVariable Long id) {
        return planejamentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conteudo/{conteudoId}")
    @Operation(summary = "Buscar planejamento por conteúdo")
    @ApiResponse(responseCode = "200", description = "Planejamento encontrado")
    public ResponseEntity<PlanejamentoEstudo> buscarPorConteudo(@PathVariable Long conteudoId) {
        return planejamentoService.buscarPorConteudo(conteudoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos os planejamentos")
    @ApiResponse(responseCode = "200", description = "Lista de planejamentos")
    public ResponseEntity<List<PlanejamentoEstudo>> listarTodos() {
        return ResponseEntity.ok(planejamentoService.listarTodos());
    }

    @PostMapping
    @Operation(summary = "Criar novo planejamento de estudo")
    @ApiResponse(responseCode = "201", description = "Planejamento criado com sucesso")
    public ResponseEntity<PlanejamentoEstudo> criar(
            @RequestParam Long conteudoId,
            @RequestParam Double horasPlanejadas,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        try {
            PlanejamentoEstudo planejamento = planejamentoService.criar(conteudoId, horasPlanejadas, dataInicio, dataFim);
            return ResponseEntity.status(HttpStatus.CREATED).body(planejamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar planejamento")
    @ApiResponse(responseCode = "200", description = "Planejamento atualizado")
    public ResponseEntity<PlanejamentoEstudo> atualizar(
            @PathVariable Long id,
            @RequestParam Double horasPlanejadas,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        try {
            PlanejamentoEstudo planejamento = planejamentoService.atualizar(id, horasPlanejadas, dataInicio, dataFim);
            return ResponseEntity.ok(planejamento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/horas-em-minutos")
    @Operation(summary = "Converter horas planejadas em minutos")
    @ApiResponse(responseCode = "200", description = "Conversão realizada")
    public ResponseEntity<Map<String, Integer>> horasEmMinutos(@PathVariable Long id) {
        try {
            Integer minutos = planejamentoService.horasEmMinutos(id);
            Map<String, Integer> response = new HashMap<>();
            response.put("minutos", minutos);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar planejamento")
    @ApiResponse(responseCode = "204", description = "Planejamento deletado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planejamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
