package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão de conteúdos planejados.
 */
@Service
public class ConteudoService {

    @Autowired
    private ConteudoPlanejadoRepository conteudoRepository;

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    /**
     * Busca um conteúdo pelo ID.
     */
    public Optional<ConteudoPlanejado> buscarPorId(Long id) {
        return conteudoRepository.findById(id);
    }

    /**
     * Lista todos os conteúdos de uma tecnologia.
     */
    public List<ConteudoPlanejado> listarPorTecnologia(Long tecnologiaId) {
        return conteudoRepository.findByTecnologiaId(tecnologiaId);
    }

    /**
     * Lista conteúdos de uma tecnologia por status.
     */
    public List<ConteudoPlanejado> listarPorTecnologiaEStatus(Long tecnologiaId, StatusConteudo status) {
        return conteudoRepository.findByTecnologiaIdAndStatus(tecnologiaId, status);
    }

    /**
     * Lista conteúdos concluídos de uma tecnologia.
     */
    public List<ConteudoPlanejado> listarConcluidosPorTecnologia(Long tecnologiaId) {
        return conteudoRepository.findByTecnologiaIdAndStatus(tecnologiaId, StatusConteudo.CONCLUIDO);
    }

    /**
     * Cria um novo conteúdo para uma tecnologia.
     */
    public ConteudoPlanejado criar(Long tecnologiaId, String titulo, String descricao) {
        Tecnologia tecnologia = tecnologiaRepository.findById(tecnologiaId)
                .orElseThrow(() -> new RuntimeException("Tecnologia não encontrada"));

        ConteudoPlanejado conteudo = new ConteudoPlanejado(titulo, descricao, tecnologia);
        return conteudoRepository.save(conteudo);
    }

    /**
     * Atualiza um conteúdo.
     */
    public ConteudoPlanejado atualizar(Long id, String titulo, String descricao) {
        ConteudoPlanejado conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        conteudo.setTitulo(titulo);
        conteudo.setDescricao(descricao);
        
        return conteudoRepository.save(conteudo);
    }

    /**
     * Marca um conteúdo como em andamento.
     */
    public ConteudoPlanejado iniciar(Long id) {
        ConteudoPlanejado conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        if (conteudo.getStatus() != StatusConteudo.NAO_INICIADO) {
            throw new IllegalArgumentException("Conteúdo não pode ser iniciado neste status");
        }

        conteudo.setStatus(StatusConteudo.EM_ANDAMENTO);
        return conteudoRepository.save(conteudo);
    }

    /**
     * Marca um conteúdo como concluído com nível de domínio.
     */
    public ConteudoPlanejado concluir(Long id, NivelDominio nivelDominio) {
        ConteudoPlanejado conteudo = conteudoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conteúdo não encontrado"));

        if (nivelDominio == null) {
            throw new IllegalArgumentException("Nível de domínio é obrigatório para concluir");
        }

        conteudo.setStatus(StatusConteudo.CONCLUIDO);
        conteudo.setNivelDominio(nivelDominio);
        
        return conteudoRepository.save(conteudo);
    }

    /**
     * Deleta um conteúdo.
     */
    public void deletar(Long id) {
        conteudoRepository.deleteById(id);
    }

    /**
     * Calcula o progresso de uma tecnologia (% de conteúdos concluídos).
     */
    public Double calcularProgressoTecnologia(Long tecnologiaId) {
        List<ConteudoPlanejado> todosConteudos = conteudoRepository.findByTecnologiaId(tecnologiaId);
        
        if (todosConteudos.isEmpty()) {
            return 0.0;
        }

        long concluidosCount = todosConteudos.stream()
                .filter(c -> c.getStatus() == StatusConteudo.CONCLUIDO)
                .count();

        return (concluidosCount * 100.0) / todosConteudos.size();
    }
}
