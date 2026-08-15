package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão de tecnologias e disciplinas.
 */
@Service
public class TecnologiaService {

    @Autowired
    private TecnologiaRepository tecnologiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca uma tecnologia pelo ID.
     */
    public Optional<Tecnologia> buscarPorId(Long id) {
        return tecnologiaRepository.findById(id);
    }

    /**
     * Lista todas as tecnologias de um usuário.
     */
    public List<Tecnologia> listarPorUsuario(Long usuarioId) {
        return tecnologiaRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Lista todas as tecnologias ativas de um usuário.
     */
    public List<Tecnologia> listarAtivasPorUsuario(Long usuarioId) {
        return tecnologiaRepository.findByUsuarioIdAndStatusAtiva(usuarioId, StatusTecnologia.ATIVA);
    }

    /**
     * Cria uma nova tecnologia para o usuário.
     */
    public Tecnologia criar(Long usuarioId, String nome, String descricao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Validar se tecnologia já existe para este usuário
        List<Tecnologia> existentes = tecnologiaRepository.findByUsuarioIdAndNome(usuarioId, nome);
        if (!existentes.isEmpty()) {
            throw new IllegalArgumentException("Tecnologia já cadastrada para este usuário");
        }

        Tecnologia tecnologia = new Tecnologia(nome, descricao, usuario);
        return tecnologiaRepository.save(tecnologia);
    }

    /**
     * Atualiza uma tecnologia existente.
     */
    public Tecnologia atualizar(Long id, String nome, String descricao) {
        Tecnologia tecnologia = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia não encontrado"));

        // Validar se novo nome já existe para este usuário
        if (!tecnologia.getNome().equals(nome)) {
            List<Tecnologia> existentes = tecnologiaRepository
                    .findByUsuarioIdAndNome(tecnologia.getUsuario().getId(), nome);
            if (!existentes.isEmpty()) {
                throw new IllegalArgumentException("Tecnologia já cadastrada para este usuário");
            }
        }

        tecnologia.setNome(nome);
        tecnologia.setDescricao(descricao);
        
        return tecnologiaRepository.save(tecnologia);
    }

    /**
     * Ativa uma tecnologia.
     */
    public Tecnologia ativar(Long id) {
        Tecnologia tecnologia = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia não encontrado"));
        
        tecnologia.setStatus(StatusTecnologia.ATIVA);
        return tecnologiaRepository.save(tecnologia);
    }

    /**
     * Inativa uma tecnologia.
     */
    public Tecnologia inativar(Long id) {
        Tecnologia tecnologia = tecnologiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tecnologia não encontrado"));
        
        tecnologia.setStatus(StatusTecnologia.INATIVA);
        return tecnologiaRepository.save(tecnologia);
    }

    /**
     * Deleta uma tecnologia.
     */
    public void deletar(Long id) {
        tecnologiaRepository.deleteById(id);
    }
}
