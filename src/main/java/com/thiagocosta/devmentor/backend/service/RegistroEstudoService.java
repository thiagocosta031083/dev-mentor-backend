package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import com.thiagocosta.devmentor.backend.repository.RegistroEstudoRepository;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelo registro de estudos realizados.
 */
@Service
public class RegistroEstudoService {

    @Autowired
    private RegistroEstudoRepository registroRepository;

    @Autowired
    private ConteudoPlanejadoRepository conteudoRepository;

    /**
     * Busca um registro pelo ID.
     */
    public Optional<RegistroEstudo> buscarPorId(Long id) {
        return registroRepository.findById(id);
    }

    /**
     * Lista todos os registros de um conteúdo.
     */
    public List<RegistroEstudo> listarPorConteudo(Long conteudoId) {
        return registroRepository.findByConteudoId(conteudoId);
    }

    /**
     * Lista registros por conteúdo e tipo de estudo.
     */
    public List<RegistroEstudo> listarPorConteudoETipo(Long conteudoId, TipoEstudo tipo) {
        return registroRepository.findByConteudoIdAndTipo(conteudoId, tipo);
    }

    /**
     * Lista registros de um período específico.
     */
    public List<RegistroEstudo> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return registroRepository.findByDataBetween(dataInicio, dataFim);
    }

    /**
     * Registra um novo estudo.
     */
    public RegistroEstudo registrar(Long conteudoId, LocalDate data, Integer tempoMinutos, TipoEstudo tipo, String observacoes) {
        ConteudoPlanejado conteudo = conteudoRepository.findById(conteudoId)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        // Validar tempo mínimo
        if (tempoMinutos == null || tempoMinutos <= 0) {
            throw new IllegalArgumentException("Tempo de estudo deve ser maior que zero");
        }

        RegistroEstudo registro = new RegistroEstudo(data, tempoMinutos, tipo, observacoes, conteudo);
        return registroRepository.save(registro);
    }

    /**
     * Atualiza um registro de estudo.
     */
    public RegistroEstudo atualizar(Long id, LocalDate data, Integer tempoMinutos, TipoEstudo tipo, String observacoes) {
        RegistroEstudo registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de estudo não encontrado"));

        if (tempoMinutos != null && tempoMinutos <= 0) {
            throw new IllegalArgumentException("Tempo de estudo deve ser maior que zero");
        }

        registro.setData(data);
        if (tempoMinutos != null) {
            registro.setTempoMinutos(tempoMinutos);
        }
        registro.setTipo(tipo);
        registro.setObservacoes(observacoes);

        return registroRepository.save(registro);
    }

    /**
     * Deleta um registro.
     */
    public void deletar(Long id) {
        registroRepository.deleteById(id);
    }

    /**
     * Calcula total de horas estudadas em um conteúdo.
     */
    public Double calcularTotalHorasConteudo(Long conteudoId) {
        List<RegistroEstudo> registros = registroRepository.findByConteudoId(conteudoId);
        
        int totalMinutos = registros.stream()
                .mapToInt(RegistroEstudo::getTempoMinutos)
                .sum();

        return totalMinutos / 60.0;
    }

    /**
     * Calcula total de horas estudadas em um período.
     */
    public Double calcularTotalHorasPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        List<RegistroEstudo> registros = registroRepository.findByDataBetween(dataInicio, dataFim);
        
        int totalMinutos = registros.stream()
                .mapToInt(RegistroEstudo::getTempoMinutos)
                .sum();

        return totalMinutos / 60.0;
    }

    /**
     * Calcula total de minutos estudados por tipo em um conteúdo.
     */
    public Integer calcularMinutosPorTipo(Long conteudoId, TipoEstudo tipo) {
        List<RegistroEstudo> registros = registroRepository.findByConteudoIdAndTipo(conteudoId, tipo);
        
        return registros.stream()
                .mapToInt(RegistroEstudo::getTempoMinutos)
                .sum();
    }
}
