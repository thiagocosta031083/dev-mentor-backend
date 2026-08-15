package com.thiagocosta.devmentor.backend.dto.request;

import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import jakarta.validation.constraints.*;

public class ProjetoPessoalRequestDTO {
    @NotBlank @Size(max = 150) private String nome;
    @NotBlank @Size(max = 200) private String stack;
    @NotNull private StatusProjeto status;
    @Size(max = 300) private String proximoPasso;
    private Long tecnologiaId;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getStack() { return stack; }
    public void setStack(String stack) { this.stack = stack; }
    public StatusProjeto getStatus() { return status; }
    public void setStatus(StatusProjeto status) { this.status = status; }
    public String getProximoPasso() { return proximoPasso; }
    public void setProximoPasso(String passo) { this.proximoPasso = passo; }
    public Long getTecnologiaId() { return tecnologiaId; }
    public void setTecnologiaId(Long id) { this.tecnologiaId = id; }
}
