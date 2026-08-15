package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço responsável pelo cálculo de evolução técnica.
 * 
 * A evolução é calculada com base em:
 * - Número de conteúdos concluídos por tecnologia
 * - Nível de domínio alcançado
 * - Horas de estudo dedicadas
 */
@Service
public class EvolucaoService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private ConteudoPlanejadoRepository conteudoRepository;

    @Autowired
    private RegistroEstudoService registroEstudoService;

    /**
     * Calcula o nível médio de domínio de uma tecnologia.
     * Considera apenas conteúdos concluídos.
     */
    public Double calcularNivelMedioDominio(Long tecnologiaId) {
        List<ConteudoPlanejado> conteudosConcluidos = conteudoRepository
                .findByTecnologiaIdAndStatus(tecnologiaId, StatusConteudo.CONCLUIDO);

        if (conteudosConcluidos.isEmpty()) {
            return 0.0;
        }

        double somaNiveis = conteudosConcluidos.stream()
                .map(c -> c.getNivelDominio().getValor())
                .mapToDouble(Double::valueOf)
                .sum();

        return somaNiveis / conteudosConcluidos.size();
    }

    /**
     * Calcula a evolução geral (0 a 100) de uma tecnologia.
     * 
     * Fórmula:
     * - 30% = Taxa de conclusão (conteúdos concluídos / total)
     * - 50% = Nível médio de domínio (média dos níveis / 4)
     * - 20% = Horas de estudo (normalizado)
     */
    public Double calcularEvolucaoTecnologia(Long tecnologiaId, Integer horasMaximasReferencia) {
        if (horasMaximasReferencia == null) {
            horasMaximasReferencia = 40; // 40 horas como referência
        }

        List<ConteudoPlanejado> todosConteudos = conteudoRepository.findByTecnologiaId(tecnologiaId);

        if (todosConteudos.isEmpty()) {
            return 0.0;
        }

        // 1. Taxa de conclusão (30%)
        long concluidosCount = todosConteudos.stream()
                .filter(c -> c.getStatus() == StatusConteudo.CONCLUIDO)
                .count();
        double taxaConclusao = (concluidosCount * 100.0) / todosConteudos.size();
        double componenteConclusao = (taxaConclusao / 100.0) * 30;

        // 2. Nível médio de domínio (50%)
        double nivelMedio = calcularNivelMedioDominio(tecnologiaId);
        double componenteNivel = (nivelMedio / 4.0) * 50;

        // 3. Horas de estudo (20%)
        double horasEstudadas = registroEstudoService.calcularTotalHorasConteudo(tecnologiaId);
        double taxaHoras = Math.min((horasEstudadas / horasMaximasReferencia) * 100, 100);
        double componenteHoras = (taxaHoras / 100.0) * 20;

        return componenteConclusao + componenteNivel + componenteHoras;
    }

    /**
     * Calcula a evolução geral do usuário.
     * Média ponderada das evoluções de todas as tecnologias ativas.
     */
    public Double calcularEvolucaoGeral(Long usuarioId) {
        List<Tecnologia> tecnologias = tecnologiaRepository.findByUsuarioId(usuarioId);

        if (tecnologias.isEmpty()) {
            return 0.0;
        }

        double somaEvolucoes = tecnologias.stream()
                .mapToDouble(t -> calcularEvolucaoTecnologia(t.getId(), null))
                .sum();

        return somaEvolucoes / tecnologias.size();
    }

    /**
     * Gera um mapa com a evolução de cada tecnologia do usuário.
     */
    public Map<String, Double> calcularEvolucaoPorTecnologia(Long usuarioId) {
        return tecnologiaRepository.findByUsuarioId(usuarioId)
                .stream()
                .collect(Collectors.toMap(
                        Tecnologia::getNome,
                        t -> calcularEvolucaoTecnologia(t.getId(), null)
                ));
    }

    /**
     * Classifica o nível de proficiência em uma tecnologia.
     * Retorna: "Iniciante", "Intermediário", "Avançado", "Expert"
     */
    public String classificarProficiencia(Long tecnologiaId) {
        Double evolucao = calcularEvolucaoTecnologia(tecnologiaId, null);

        if (evolucao < 25) {
            return "Iniciante";
        } else if (evolucao < 50) {
            return "Intermediário";
        } else if (evolucao < 75) {
            return "Avançado";
        } else {
            return "Expert";
        }
    }

    /**
     * Calcula o progresso em relação a um objetivo (0-100).
     */
    public Double calcularProgressoEmRelacaoObjetivo(Long tecnologiaId, Integer horasObjetivo) {
        Double horasEstudadas = registroEstudoService.calcularTotalHorasConteudo(tecnologiaId);
        return Math.min((horasEstudadas / horasObjetivo) * 100, 100);
    }
}
