package com.thiagocosta.devmentor.backend.domain.model;

import javax.persistence.*;

@Entity
@Table (name = "tecnologia")
public class Tecnologia {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected Tecnologia(){

    }

    public Tecnologia(String nome, String descircao, Usuario usuario) {
        this.nome = nome;
        this.descricao = descircao;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
