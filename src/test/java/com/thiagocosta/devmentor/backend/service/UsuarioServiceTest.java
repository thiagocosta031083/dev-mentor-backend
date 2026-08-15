package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João Silva", "joao@example.com");
    }

    @Test
    void testCreateUsuarioComSucesso() {
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.criar(usuario);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testCreateUsuarioComEmailDuplicado() {
        when(usuarioRepository.findByEmail("joao@example.com")).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class, () -> usuarioService.criar(usuario));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void testObterUsuarioPorIdComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obter(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void testObterUsuarioNaoEncontrado() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.obter(99L));
    }

    @Test
    void testAtualizarUsuarioComSucesso() {
        usuario.setNome("João Silva Atualizado");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.atualizar(1L, usuario);

        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", resultado.getNome());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testDeletarUsuarioComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        usuarioService.deletar(1L);

        verify(usuarioRepository, times(1)).delete(usuario);
    }

}
