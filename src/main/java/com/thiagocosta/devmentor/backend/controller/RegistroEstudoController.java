package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import com.thiagocosta.devmentor.backend.service.RegistroEstudoService;
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
@RequestMapping("/api/v1/registros-estudo")
@Tag(name = "Registros de Estudo", description = "Endpoints para gestão de registros de estudos realizados")
public class RegistroEstudoController {

    @Autowired
    private RegistroEstudoService registroService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    public ResponseEntity<RegistroEstudo> buscarPorId(@PathVariable Long id) {
        return registroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conteudo/{conteudoId}")
    @Operation(summary = "Listar registros de um conteúdo")
    @ApiResponse(responseCode = "200", description = "Lista de registros")
    public ResponseEntity<List<RegistroEstudo>> listarPorConteudo(@PathVariable Long conteudoId) {
        return ResponseEntity.ok(registroService.listarPorConteudo(conteudoId));
    }

    @GetMapping("/conteudo/{conteudoId}/tipo")
    @Operation(summary = "Listar registros por conteúdo e tipo de estudo")
    @ApiResponse(responseCode = "200", description = "Lista de registros")
    public ResponseEntity<List<RegistroEstudo>> listarPorTipo(@PathVariable Long conteudoId, @RequestParam TipoEstudo tipo) {
        return ResponseEntity.ok(registroService.listarPorConteudoETipo(conteudoId, tipo));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar registros de um período")
    @ApiResponse(responseCode = "200", description = "Lista de registros")
    public ResponseEntity<List<RegistroEstudo>> listarPorPeriodo(@RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) {
        return ResponseEntity.ok(registroService.listarPorPeriodo(dataInicio, dataFim));
    }

    @PostMapping
    @Operation(summary = "Registrar um novo estudo")
    @ApiResponse(responseCode = "201", description = "Registro criado com sucesso")
    public ResponseEntity<RegistroEstudo> registrar(
            @RequestParam Long conteudoId,
            @RequestParam LocalDate data,
            @RequestParam Integer tempoMinutos,
            @RequestParam TipoEstudo tipo,
            @RequestParam(required = false) String observacoes) {
        try {
            RegistroEstudo registro = registroService.registrar(conteudoId, data, tempoMinutos, tipo, observacoes);
            return ResponseEntity.status(HttpStatus.CREATED).body(registro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro de estudo")
    @ApiResponse(responseCode = "200", description = "Registro atualizado")
    public ResponseEntity<RegistroEstudo> atualizar(
            @PathVariable Long id,
            @RequestParam LocalDate data,
            @RequestParam(required = false) Integer tempoMinutos,
            @RequestParam TipoEstudo tipo,
            @RequestParam(required = false) String observacoes) {
        try {
            RegistroEstudo registro = registroService.atualizar(id, data, tempoMinutos, tipo, observacoes);
            return ResponseEntity.ok(registro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/conteudo/{conteudoId}/total-horas")
    @Operation(summary = "Calcular total de horas estudadas em um conteúdo")
    @ApiResponse(responseCode = "200", description = "Total calculado")
    public ResponseEntity<Map<String, Double>> calcularTotalHoras(@PathVariable Long conteudoId) {
        Double totalHoras = registroService.calcularTotalHorasConteudo(conteudoId);
        Map<String, Double> response = new HashMap<>();
        response.put("totalHoras", totalHoras);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo/total-horas")
    @Operation(summary = "Calcular total de horas em um período")
    @ApiResponse(responseCode = "200", description = "Total calculado")
    public ResponseEntity<Map<String, Double>> calcularTotalHorasPeriodo(@RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) {
        Double totalHoras = registroService.calcularTotalHorasPeriodo(dataInicio, dataFim);
        Map<String, Double> response = new HashMap<>();
        response.put("totalHoras", totalHoras);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro")
    @ApiResponse(responseCode = "204", description = "Registro deletado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        registroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
