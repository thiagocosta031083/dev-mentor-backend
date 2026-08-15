package com.thiagocosta.devmentor.backend.security;

import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository repository;
    public CustomUserDetailsService(UsuarioRepository repository){this.repository=repository;}
    @Override public UserDetails loadUserByUsername(String email){
        Usuario u=repository.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));
        return User.withUsername(u.getEmail()).password(u.getSenhaHash()).authorities(Collections.emptyList()).build();
    }
}
