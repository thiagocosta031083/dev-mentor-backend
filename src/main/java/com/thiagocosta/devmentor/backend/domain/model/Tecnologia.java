package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusTecnologia status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected Tecnologia(){

    }

    public Tecnologia(String nome, String descircao, Usuario usuario) {
        this.nome = nome;
        this.descricao = descircao;
        this.usuario = usuario;
        this.status = StatusTecnologia.ATIVA;
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

    public StatusTecnologia getStatus() {
        return status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(StatusTecnologia status) {
        this.status = status;
    }
}
