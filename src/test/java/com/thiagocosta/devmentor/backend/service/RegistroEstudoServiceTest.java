package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.RegistroEstudoRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroEstudoServiceTest {

    @Mock
    private RegistroEstudoRepository registroRepository;

    @Mock
    private ConteudoPlanejadoRepository conteudoRepository;

    @InjectMocks
    private RegistroEstudoService registroService;

    private RegistroEstudo registro;
    private ConteudoPlanejado conteudo;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("João", "joao@example.com");
        Tecnologia tecnologia = new Tecnologia("Java", "Linguagem", usuario);
        conteudo = new ConteudoPlanejado("Collections", "Aprenda Collections", tecnologia);
        registro = new RegistroEstudo(
            LocalDate.of(2026, 8, 14), 
            120, 
            TipoEstudo.PRATICO, 
            "Bom progress", 
            conteudo
        );
    }

    @Test
    void testObterRegistroComSucesso() {
        when(registroRepository.findById(1L)).thenReturn(Optional.of(registro));

        RegistroEstudo resultado = registroService.obter(1L);

        assertNotNull(resultado);
        assertEquals(120, resultado.getTempoMinutos());
    }

    @Test
    void testDeletarRegistroComSucesso() {
        when(registroRepository.findById(1L)).thenReturn(Optional.of(registro));
        doNothing().when(registroRepository).delete(registro);

        registroService.deletar(1L);

        verify(registroRepository, times(1)).delete(registro);
    }

}
