package com.thiagocosta.devmentor.backend.config;

import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialUserConfig {
    @Bean
    CommandLineRunner initialUser(
            UsuarioRepository repository,
            PasswordEncoder encoder,
            @Value("${app.initial-user.name}") String nome,
            @Value("${app.initial-user.email}") String email,
            @Value("${app.initial-user.password}") String senha) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Usuario(nome, email.toLowerCase(), encoder.encode(senha)));
            }
        };
    }
}
