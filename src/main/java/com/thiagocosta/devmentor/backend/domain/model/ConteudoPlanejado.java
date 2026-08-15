package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.*;
import jakarta.persistence.*;

@Entity
@Table(name = "conteudo_planejado")
public class ConteudoPlanejado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnologia_id", nullable = false)
    private Tecnologia tecnologia;
    @Column(nullable = false, length = 150)
    private String titulo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private TipoConteudo tipo;
    @Column(nullable = false)
    private Integer peso;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private StatusConteudo status;
    @Enumerated(EnumType.STRING) @Column(name = "nivel_dominio", length = 20)
    private NivelDominio nivelDominio;

    protected ConteudoPlanejado() {}

    public ConteudoPlanejado(Tecnologia tecnologia, String titulo, TipoConteudo tipo, Integer peso) {
        this.tecnologia = tecnologia; this.titulo = titulo; this.tipo = tipo; this.peso = peso;
        this.status = StatusConteudo.NAO_INICIADO;
    }

    public Long getId() { return id; }
    public Tecnologia getTecnologia() { return tecnologia; }
    public String getTitulo() { return titulo; }
    public TipoConteudo getTipo() { return tipo; }
    public Integer getPeso() { return peso; }
    public StatusConteudo getStatus() { return status; }
    public NivelDominio getNivelDominio() { return nivelDominio; }
    public void atualizar(String titulo, TipoConteudo tipo, Integer peso) { this.titulo = titulo; this.tipo = tipo; this.peso = peso; }
    public void iniciar() { this.status = StatusConteudo.EM_ANDAMENTO; }
    public void concluir(NivelDominio nivelDominio) { this.status = StatusConteudo.CONCLUIDO; this.nivelDominio = nivelDominio; }
}
