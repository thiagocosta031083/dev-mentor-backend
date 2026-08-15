package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.exception.ResourceNotFoundException;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository){this.repository=repository;}
    public Usuario porEmail(String email){return repository.findByEmailIgnoreCase(email)
            .orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado"));}
}
