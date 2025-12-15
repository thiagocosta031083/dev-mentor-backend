package com.thiagocosta.devmentor.backend.domain.model;

import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;

import javax.persistence.*;

@Entity
@Table(name = "projeto_pessoal")
public class ProjetoPessoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 200)
    private String stack;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusProjeto status;

    @Column(name = "proximo_passo", length = 300)
    private String proximoPasso;

    /**
     * Tecnologia relacionada é opcional.
     * Serve apenas para direcionar os estudos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnologia_id")
    private Tecnologia tecnologia;

    protected ProjetoPessoal() {
        // Construtor exigido pelo JPA
    }

    public ProjetoPessoal(String nome,
                          String stack,
                          StatusProjeto status,
                          String proximoPasso,
                          Tecnologia tecnologia) {
        this.nome = nome;
        this.stack = stack;
        this.status = status;
        this.proximoPasso = proximoPasso;
        this.tecnologia = tecnologia;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getStack() {
        return stack;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public String getProximoPasso() {
        return proximoPasso;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }
}
