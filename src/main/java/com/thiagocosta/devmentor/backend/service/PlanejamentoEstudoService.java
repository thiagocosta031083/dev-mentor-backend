package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.repository.PlanejamentoEstudoRepository;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelo planejamento de horas de estudo.
 */
@Service
public class PlanejamentoEstudoService {

    @Autowired
    private PlanejamentoEstudoRepository planejamentoRepository;

    @Autowired
    private ConteudoPlanejadoRepository conteudoRepository;

    /**
     * Busca um planejamento pelo ID.
     */
    public Optional<PlanejamentoEstudo> buscarPorId(Long id) {
        return planejamentoRepository.findById(id);
    }

    /**
     * Busca planejamento por conteúdo.
     */
    public Optional<PlanejamentoEstudo> buscarPorConteudo(Long conteudoId) {
        return planejamentoRepository.findByConteudoId(conteudoId);
    }

    /**
     * Lista todos os planejamentos.
     */
    public List<PlanejamentoEstudo> listarTodos() {
        return planejamentoRepository.findAll();
    }

    /**
     * Cria um novo planejamento de horas.
     */
    public PlanejamentoEstudo criar(Long conteudoId, Double horasPlanejadas, LocalDate dataInicio, LocalDate dataFim) {
        ConteudoPlanejado conteudo = conteudoRepository.findById(conteudoId)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        // Validar se já existe planejamento para este conteúdo
        Optional<PlanejamentoEstudo> existente = planejamentoRepository.findByConteudoId(conteudoId);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe planejamento para este conteúdo");
        }

        // Validar datas
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }

        if (horasPlanejadas <= 0) {
            throw new IllegalArgumentException("Horas planejadas deve ser maior que zero");
        }

        PlanejamentoEstudo planejamento = new PlanejamentoEstudo(
                horasPlanejadas, 
                dataInicio, 
                dataFim, 
                conteudo
        );

        return planejamentoRepository.save(planejamento);
    }

    /**
     * Atualiza um planejamento.
     */
    public PlanejamentoEstudo atualizar(Long id, Double horasPlanejadas, LocalDate dataInicio, LocalDate dataFim) {
        PlanejamentoEstudo planejamento = planejamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planejamento não encontrado"));

        // Validar datas
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }

        if (horasPlanejadas <= 0) {
            throw new IllegalArgumentException("Horas planejadas deve ser maior que zero");
        }

        planejamento.setHorasPlanejadas(horasPlanejadas);
        planejamento.setDataInicio(dataInicio);
        planejamento.setDataFim(dataFim);

        return planejamentoRepository.save(planejamento);
    }

    /**
     * Deleta um planejamento.
     */
    public void deletar(Long id) {
        planejamentoRepository.deleteById(id);
    }

    /**
     * Calcula as horas planejadas em minutos.
     */
    public Integer horasEmMinutos(Long planejamentoId) {
        PlanejamentoEstudo planejamento = planejamentoRepository.findById(planejamentoId)
                .orElseThrow(() -> new RuntimeException("Planejamento não encontrado"));

        return (int) (planejamento.getHorasPlanejadas() * 60);
    }
}
