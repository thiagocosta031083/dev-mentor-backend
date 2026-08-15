package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import com.thiagocosta.devmentor.backend.repository.ProjetoPessoalRepository;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão de projetos pessoais.
 */
@Service
public class ProjetoPessoalService {

    @Autowired
    private ProjetoPessoalRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    /**
     * Busca um projeto pelo ID.
     */
    public Optional<ProjetoPessoal> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    /**
     * Lista todos os projetos de um usuário.
     */
    public List<ProjetoPessoal> listarPorUsuario(Long usuarioId) {
        return projetoRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Lista projetos em andamento de um usuário.
     */
    public List<ProjetoPessoal> listarEmAndamentoPorUsuario(Long usuarioId) {
        return projetoRepository.findByUsuarioIdAndStatus(usuarioId, StatusProjeto.EM_ANDAMENTO);
    }

    /**
     * Lista projetos concluídos de um usuário.
     */
    public List<ProjetoPessoal> listarConcluidosPorUsuario(Long usuarioId) {
        return projetoRepository.findByUsuarioIdAndStatus(usuarioId, StatusProjeto.CONCLUIDO);
    }

    /**
     * Cria um novo projeto pessoal.
     */
    public ProjetoPessoal criar(Long usuarioId, String nome, String stack, String proximoPasso, Long tecnologiaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Tecnologia tecnologia = null;
        if (tecnologiaId != null) {
            tecnologia = tecnologiaRepository.findById(tecnologiaId)
                    .orElseThrow(() -> new RuntimeException("Tecnologia não encontrada"));
        }

        ProjetoPessoal projeto = new ProjetoPessoal(
                nome,
                stack,
                StatusProjeto.IDEIA,
                proximoPasso,
                tecnologia,
                usuario
        );

        return projetoRepository.save(projeto);
    }

    /**
     * Atualiza um projeto.
     */
    public ProjetoPessoal atualizar(Long id, String nome, String stack, String proximoPasso, Long tecnologiaId) {
        ProjetoPessoal projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Tecnologia tecnologia = null;
        if (tecnologiaId != null) {
            tecnologia = tecnologiaRepository.findById(tecnologiaId)
                    .orElseThrow(() -> new RuntimeException("Tecnologia não encontrada"));
        }

        projeto.setNome(nome);
        projeto.setStack(stack);
        projeto.setProximoPasso(proximoPasso);
        projeto.setTecnologia(tecnologia);

        return projetoRepository.save(projeto);
    }

    /**
     * Inicia um projeto (muda status de IDEIA para EM_ANDAMENTO).
     */
    public ProjetoPessoal iniciar(Long id) {
        ProjetoPessoal projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (projeto.getStatus() != StatusProjeto.IDEIA) {
            throw new IllegalArgumentException("Apenas projetos em IDEIA podem ser iniciados");
        }

        projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
        return projetoRepository.save(projeto);
    }

    /**
     * Conclui um projeto.
     */
    public ProjetoPessoal concluir(Long id) {
        ProjetoPessoal projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        projeto.setStatus(StatusProjeto.CONCLUIDO);
        return projetoRepository.save(projeto);
    }

    /**
     * Deleta um projeto.
     */
    public void deletar(Long id) {
        projetoRepository.deleteById(id);
    }
}
