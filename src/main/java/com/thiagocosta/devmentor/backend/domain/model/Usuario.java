package com.thiagocosta.devmentor.backend.domain.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    protected Usuario() {
        // construtor protegido exigido pelo JPA
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

}
