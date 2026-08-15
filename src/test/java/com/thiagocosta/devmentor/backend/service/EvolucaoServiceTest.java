package com.thiagocosta.devmentor.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.thiagocosta.devmentor.backend.domain.enums.*;
import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.response.DashboardResponseDTO;
import com.thiagocosta.devmentor.backend.repository.*;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.Test;

class EvolucaoServiceTest {
    private static final LocalDate HOJE = LocalDate.of(2026, 8, 15);

    @Test
    void deveRetornarZeroSemConteudoESemPlano() {
        Fixture f = new Fixture();
        f.conteudos(Collections.emptyList());
        DashboardResponseDTO resultado = f.calcular();
        assertEquals(0.0, resultado.getCobertura());
        assertEquals(0.0, resultado.getEsforco());
        assertEquals(0.0, resultado.getPratica());
        assertEquals(0.0, resultado.getEvolucao());
        assertEquals(StatusEvolucao.DENTRO_DO_ESPERADO, resultado.getStatus());
    }

    @Test
    void deveFicarAbaixoQuandoNenhumConteudoFoiConcluido() {
        Fixture f = new Fixture();
        f.conteudos(Collections.singletonList(f.conteudo("A", 1, null)));
        f.plano(10.0, 50.0);
        DashboardResponseDTO resultado = f.calcular();
        assertEquals(0.0, resultado.getCobertura());
        assertEquals(0.0, resultado.getPratica());
        assertEquals(StatusEvolucao.ABAIXO_DO_ESPERADO, resultado.getStatus());
    }

    @Test
    void deveCalcularEvolucaoParcialMesmoSemPlano() {
        Fixture f = new Fixture();
        ConteudoPlanejado concluido = f.conteudo("A", 1, NivelDominio.NIVEL_2);
        ConteudoPlanejado pendente = f.conteudo("B", 1, null);
        f.conteudos(Arrays.asList(concluido, pendente));
        DashboardResponseDTO resultado = f.calcular();
        assertEquals(50.0, resultado.getCobertura());
        assertEquals(0.0, resultado.getEsforco());
        assertEquals(50.0, resultado.getPratica());
        assertEquals(35.0, resultado.getEvolucao());
    }

    @Test
    void deveLimitarEsforcoEmCemEPonderarDominiosUmEQuatro() {
        Fixture f = new Fixture();
        f.conteudos(
                Arrays.asList(
                        f.conteudo("A", 1, NivelDominio.NIVEL_1),
                        f.conteudo("B", 3, NivelDominio.NIVEL_4)));
        f.plano(10.0, 50.0);
        f.minutos(1200L);
        DashboardResponseDTO resultado = f.calcular();
        assertEquals(100.0, resultado.getCobertura());
        assertEquals(100.0, resultado.getEsforco());
        assertEquals(81.25, resultado.getPratica());
        assertEquals(94.38, resultado.getEvolucao());
        assertEquals(StatusEvolucao.ACIMA_DO_ESPERADO, resultado.getStatus());
    }

    private static class Fixture {
        private final TecnologiaService tecnologias = mock(TecnologiaService.class);
        private final ConteudoPlanejadoRepository conteudos =
                mock(ConteudoPlanejadoRepository.class);
        private final PlanejamentoEstudoRepository planos =
                mock(PlanejamentoEstudoRepository.class);
        private final RegistroEstudoRepository registros = mock(RegistroEstudoRepository.class);
        private final PlanoEstudoService planoService = mock(PlanoEstudoService.class);
        private final Tecnologia tecnologia;

        private Fixture() {
            Usuario usuario = new Usuario("Teste", "teste@email.com", "hash");
            tecnologia = new Tecnologia("Java", TipoTecnologia.TECNOLOGIA, null, 10.0, usuario);
            when(tecnologias.buscar(1L, "teste@email.com")).thenReturn(tecnologia);
            when(conteudos.findAllByTecnologiaIdOrderByIdAsc(1L))
                    .thenReturn(Collections.emptyList());
            when(planos.findAtivoByTecnologiaId(1L, HOJE)).thenReturn(Optional.empty());
            when(registros.somarMinutosPorTecnologia(1L)).thenReturn(0L);
        }

        private ConteudoPlanejado conteudo(String titulo, int peso, NivelDominio nivel) {
            ConteudoPlanejado conteudo =
                    new ConteudoPlanejado(tecnologia, titulo, TipoConteudo.PRATICA, peso);
            if (nivel != null) conteudo.concluir(nivel);
            return conteudo;
        }

        private void conteudos(List<ConteudoPlanejado> valores) {
            when(conteudos.findAllByTecnologiaIdOrderByIdAsc(1L)).thenReturn(valores);
        }

        private void plano(double horas, double esperado) {
            PlanejamentoEstudo plano =
                    new PlanejamentoEstudo(
                            tecnologia, HOJE.minusDays(1), HOJE.plusDays(1), horas, 5.0, null);
            when(planos.findAtivoByTecnologiaId(1L, HOJE)).thenReturn(Optional.of(plano));
            when(planoService.percentualEsperado(plano, HOJE)).thenReturn(esperado);
        }

        private void minutos(long valor) {
            when(registros.somarMinutosPorTecnologia(1L)).thenReturn(valor);
        }

        private DashboardResponseDTO calcular() {
            return new EvolucaoService(tecnologias, conteudos, planos, registros, planoService)
                    .calcular(1L, "teste@email.com", HOJE);
        }
    }
}
