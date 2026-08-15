package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão de usuários no sistema Dev Mentor.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca um usuário pelo ID.
     */
    @Cacheable(value = "usuarios", key = "#id")
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca um usuário pelo email.
     */
    @Cacheable(value = "usuarios", key = "#email")
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Lista todos os usuários.
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Cria um novo usuário.
     */
    public Usuario criar(String nome, String email) {
        // Validar se email já existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }

        Usuario usuario = new Usuario(nome, email);
        return usuarioRepository.save(usuario);
    }

    /**
     * Atualiza um usuário existente.
     */
    public Usuario atualizar(Long id, String nome, String email) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Validar se novo email já existe
        if (!usuario.getEmail().equals(email) && 
            usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Deleta um usuário.
     */
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
