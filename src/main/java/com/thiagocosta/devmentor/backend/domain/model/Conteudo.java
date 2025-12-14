package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;

import javax.persistence.*;

@Entity
@Table(name = "conteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusConteudo status;

    /**
     * Preenchido ao concluir (Nível de domínio 1..4).
     * No MVP, pode ficar nulo enquanto não concluído.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_dominio", length = 30)
    private NivelDominio nivelDominio;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnologia_id", nullable = false)
    private Tecnologia tecnologia;

    protected Conteudo() {
        // exigido pelo JPA
    }

    public Conteudo(String titulo, String descricao, Tecnologia tecnologia) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tecnologia = tecnologia;
        this.status = StatusConteudo.NAO_INICIADO;
        this.nivelDominio = null; // só ao concluir
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusConteudo getStatus() {
        return status;
    }

    public NivelDominio getNivelDominio() {
        return nivelDominio;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }
}
