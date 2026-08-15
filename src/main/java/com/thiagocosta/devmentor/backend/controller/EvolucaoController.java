package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.service.EvolucaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/evolucao")
@Tag(name = "Evolução Técnica", description = "Endpoints para cálculo de evolução técnica")
public class EvolucaoController {

    @Autowired
    private EvolucaoService evolucaoService;

    @GetMapping("/tecnologia/{tecnologiaId}")
    @Operation(summary = "Calcular evolução de uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Evolução calculada")
    public ResponseEntity<Map<String, Object>> calcularEvolucaoTecnologia(
            @PathVariable Long tecnologiaId,
            @RequestParam(required = false) Integer horasMaximas) {
        Double evolucao = evolucaoService.calcularEvolucaoTecnologia(tecnologiaId, horasMaximas);
        Map<String, Object> response = new HashMap<>();
        response.put("tecnologiaId", tecnologiaId);
        response.put("evolucao", evolucao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tecnologia/{tecnologiaId}/nivel-dominio")
    @Operation(summary = "Calcular nível médio de domínio de uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Nível calculado")
    public ResponseEntity<Map<String, Object>> calcularNivelMedioDominio(@PathVariable Long tecnologiaId) {
        Double nivel = evolucaoService.calcularNivelMedioDominio(tecnologiaId);
        Map<String, Object> response = new HashMap<>();
        response.put("tecnologiaId", tecnologiaId);
        response.put("nivelMedioDominio", nivel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Calcular evolução geral do usuário")
    @ApiResponse(responseCode = "200", description = "Evolução calculada")
    public ResponseEntity<Map<String, Object>> calcularEvolucaoGeral(@PathVariable Long usuarioId) {
        Double evolucao = evolucaoService.calcularEvolucaoGeral(usuarioId);
        Map<String, Object> response = new HashMap<>();
        response.put("usuarioId", usuarioId);
        response.put("evolucaoGeral", evolucao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}/por-tecnologia")
    @Operation(summary = "Calcular evolução por tecnologia do usuário")
    @ApiResponse(responseCode = "200", description = "Evolução calculada")
    public ResponseEntity<Map<String, Object>> calcularEvolucaoPorTecnologia(@PathVariable Long usuarioId) {
        Map<String, Double> evolucaoPorTecnologia = evolucaoService.calcularEvolucaoPorTecnologia(usuarioId);
        Map<String, Object> response = new HashMap<>();
        response.put("usuarioId", usuarioId);
        response.put("evolucaoPorTecnologia", evolucaoPorTecnologia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tecnologia/{tecnologiaId}/proficiencia")
    @Operation(summary = "Classificar proficiência em uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Proficiência classificada")
    public ResponseEntity<Map<String, Object>> classificarProficiencia(@PathVariable Long tecnologiaId) {
        String proficiencia = evolucaoService.classificarProficiencia(tecnologiaId);
        Map<String, Object> response = new HashMap<>();
        response.put("tecnologiaId", tecnologiaId);
        response.put("proficiencia", proficiencia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tecnologia/{tecnologiaId}/progresso-objetivo")
    @Operation(summary = "Calcular progresso em relação a um objetivo de horas")
    @ApiResponse(responseCode = "200", description = "Progresso calculado")
    public ResponseEntity<Map<String, Object>> calcularProgressoObjetivo(
            @PathVariable Long tecnologiaId,
            @RequestParam Integer horasObjetivo) {
        Double progresso = evolucaoService.calcularProgressoEmRelacaoObjetivo(tecnologiaId, horasObjetivo);
        Map<String, Object> response = new HashMap<>();
        response.put("tecnologiaId", tecnologiaId);
        response.put("horasObjetivo", horasObjetivo);
        response.put("progresso", progresso);
        return ResponseEntity.ok(response);
    }
}
