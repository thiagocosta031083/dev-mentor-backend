package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.PlanejamentoEstudoRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanejamentoEstudoServiceTest {

    @Mock
    private PlanejamentoEstudoRepository planejamentoRepository;

    @Mock
    private ConteudoPlanejadoRepository conteudoRepository;

    @InjectMocks
    private PlanejamentoEstudoService planejamentoService;

    private PlanejamentoEstudo planejamento;
    private ConteudoPlanejado conteudo;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("João", "joao@example.com");
        Tecnologia tecnologia = new Tecnologia("Java", "Linguagem", usuario);
        conteudo = new ConteudoPlanejado("Collections", "Aprenda Collections", tecnologia);
        planejamento = new PlanejamentoEstudo(conteudo, 10L, LocalDate.of(2026, 8, 14), LocalDate.of(2026, 8, 21));
    }

    @Test
    void testObterPlanejamentoComSucesso() {
        when(planejamentoRepository.findById(1L)).thenReturn(Optional.of(planejamento));

        PlanejamentoEstudo resultado = planejamentoService.obter(1L);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getHorasPlanejadas());
    }

    @Test
    void testDeletarPlanejamentoComSucesso() {
        when(planejamentoRepository.findById(1L)).thenReturn(Optional.of(planejamento));
        doNothing().when(planejamentoRepository).delete(planejamento);

        planejamentoService.deletar(1L);

        verify(planejamentoRepository, times(1)).delete(planejamento);
    }

}
