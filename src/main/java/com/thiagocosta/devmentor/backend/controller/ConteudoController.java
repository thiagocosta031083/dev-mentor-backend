package com.thiagocosta.devmentor.backend.controller;

import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.service.ConteudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/conteudos")
@Tag(name = "Conteudos", description = "Endpoints para gestão de conteúdos planejados")
public class ConteudoController {

    @Autowired
    private ConteudoService conteudoService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conteúdo por ID")
    @ApiResponse(responseCode = "200", description = "Conteúdo encontrado")
    public ResponseEntity<ConteudoPlanejado> buscarPorId(@PathVariable Long id) {
        return conteudoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tecnologia/{tecnologiaId}")
    @Operation(summary = "Listar conteúdos de uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Lista de conteúdos")
    public ResponseEntity<List<ConteudoPlanejado>> listarPorTecnologia(@PathVariable Long tecnologiaId) {
        return ResponseEntity.ok(conteudoService.listarPorTecnologia(tecnologiaId));
    }

    @GetMapping("/tecnologia/{tecnologiaId}/status")
    @Operation(summary = "Listar conteúdos por status")
    @ApiResponse(responseCode = "200", description = "Lista de conteúdos")
    public ResponseEntity<List<ConteudoPlanejado>> listarPorStatus(@PathVariable Long tecnologiaId, @RequestParam StatusConteudo status) {
        return ResponseEntity.ok(conteudoService.listarPorTecnologiaEStatus(tecnologiaId, status));
    }

    @PostMapping
    @Operation(summary = "Criar novo conteúdo")
    @ApiResponse(responseCode = "201", description = "Conteúdo criado com sucesso")
    public ResponseEntity<ConteudoPlanejado> criar(@RequestParam Long tecnologiaId, @RequestParam String titulo, @RequestParam(required = false) String descricao) {
        try {
            ConteudoPlanejado conteudo = conteudoService.criar(tecnologiaId, titulo, descricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(conteudo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conteúdo")
    @ApiResponse(responseCode = "200", description = "Conteúdo atualizado")
    public ResponseEntity<ConteudoPlanejado> atualizar(@PathVariable Long id, @RequestParam String titulo, @RequestParam(required = false) String descricao) {
        try {
            ConteudoPlanejado conteudo = conteudoService.atualizar(id, titulo, descricao);
            return ResponseEntity.ok(conteudo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/iniciar")
    @Operation(summary = "Iniciar um conteúdo")
    @ApiResponse(responseCode = "200", description = "Conteúdo iniciado")
    public ResponseEntity<ConteudoPlanejado> iniciar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(conteudoService.iniciar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/concluir")
    @Operation(summary = "Concluir um conteúdo com nível de domínio")
    @ApiResponse(responseCode = "200", description = "Conteúdo concluído")
    public ResponseEntity<ConteudoPlanejado> concluir(@PathVariable Long id, @RequestParam NivelDominio nivelDominio) {
        try {
            return ResponseEntity.ok(conteudoService.concluir(id, nivelDominio));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tecnologia/{tecnologiaId}/progresso")
    @Operation(summary = "Calcular progresso de uma tecnologia")
    @ApiResponse(responseCode = "200", description = "Progresso calculado")
    public ResponseEntity<Map<String, Object>> calcularProgresso(@PathVariable Long tecnologiaId) {
        try {
            Double progresso = conteudoService.calcularProgressoTecnologia(tecnologiaId);
            Map<String, Object> response = new HashMap<>();
            response.put("progresso", progresso);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar conteúdo")
    @ApiResponse(responseCode = "204", description = "Conteúdo deletado")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        conteudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
