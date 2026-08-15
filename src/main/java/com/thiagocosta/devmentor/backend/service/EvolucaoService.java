package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.domain.enums.StatusEvolucao;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.dto.response.DashboardResponseDTO;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.PlanejamentoEstudoRepository;
import com.thiagocosta.devmentor.backend.repository.RegistroEstudoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EvolucaoService {
    private static final double TOLERANCIA_PERCENTUAL = 5.0;

    private final TecnologiaService tecnologias;
    private final ConteudoPlanejadoRepository conteudos;
    private final PlanejamentoEstudoRepository planos;
    private final RegistroEstudoRepository registros;
    private final PlanoEstudoService planoService;

    public EvolucaoService(TecnologiaService tecnologias,
                           ConteudoPlanejadoRepository conteudos,
                           PlanejamentoEstudoRepository planos,
                           RegistroEstudoRepository registros,
                           PlanoEstudoService planoService) {
        this.tecnologias = tecnologias;
        this.conteudos = conteudos;
        this.planos = planos;
        this.registros = registros;
        this.planoService = planoService;
    }

    @Transactional(readOnly = true)
    public DashboardResponseDTO calcular(Long tecnologiaId, String email, LocalDate hoje) {
        Tecnologia tecnologia = tecnologias.buscar(tecnologiaId, email);
        List<ConteudoPlanejado> todos = conteudos.findAllByTecnologiaIdOrderByIdAsc(tecnologiaId);
        long concluidos = todos.stream().filter(c -> c.getStatus() == StatusConteudo.CONCLUIDO).count();
        double cobertura = todos.isEmpty() ? 0 : concluidos * 100.0 / todos.size();

        Optional<PlanejamentoEstudo> plano = planos.findAtivoByTecnologiaId(tecnologiaId, hoje);
        double horasPlanejadas = plano.map(PlanejamentoEstudo::getHorasPlanejadasTotais).orElse(0.0);
        double percentualEsperado = plano.map(p -> planoService.percentualEsperado(p, hoje)).orElse(0.0);
        Long minutos = registros.somarMinutosPorTecnologia(tecnologiaId);
        double horasRealizadas = (minutos == null ? 0 : minutos) / 60.0;
        double esforco = horasPlanejadas <= 0 ? 0 : Math.min(horasRealizadas / horasPlanejadas * 100.0, 100.0);
        double pratica = calcularPratica(todos);
        double evolucao = cobertura * 0.40 + esforco * 0.30 + pratica * 0.30;
        StatusEvolucao status = classificar(evolucao, percentualEsperado);

        return new DashboardResponseDTO(tecnologia.getNome(), arredondar(horasPlanejadas),
                arredondar(horasRealizadas), todos.size(), concluidos, arredondar(cobertura),
                arredondar(esforco), arredondar(pratica), arredondar(evolucao),
                arredondar(percentualEsperado), status);
    }

    private double calcularPratica(List<ConteudoPlanejado> conteudos) {
        double somaPonderada = 0;
        int somaPesos = 0;
        for (ConteudoPlanejado conteudo : conteudos) {
            NivelDominio nivel = conteudo.getNivelDominio();
            if (conteudo.getStatus() == StatusConteudo.CONCLUIDO && nivel != null) {
                somaPonderada += (nivel.getValor() / 4.0 * 100.0) * conteudo.getPeso();
                somaPesos += conteudo.getPeso();
            }
        }
        return somaPesos == 0 ? 0 : somaPonderada / somaPesos;
    }

    private StatusEvolucao classificar(double evolucao, double esperado) {
        if (evolucao < esperado - TOLERANCIA_PERCENTUAL) return StatusEvolucao.ABAIXO_DO_ESPERADO;
        if (evolucao > esperado + TOLERANCIA_PERCENTUAL) return StatusEvolucao.ACIMA_DO_ESPERADO;
        return StatusEvolucao.DENTRO_DO_ESPERADO;
    }

    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
