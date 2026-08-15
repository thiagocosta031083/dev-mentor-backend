package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.request.RegistroEstudoRequestDTO;
import com.thiagocosta.devmentor.backend.exception.*;
import com.thiagocosta.devmentor.backend.repository.RegistroEstudoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RegistroEstudoService {
    private final RegistroEstudoRepository repository; private final TecnologiaService tecnologias; private final ConteudoService conteudos;
    public RegistroEstudoService(RegistroEstudoRepository repository,TecnologiaService tecnologias,ConteudoService conteudos){
        this.repository=repository;this.tecnologias=tecnologias;this.conteudos=conteudos;}
    @Transactional(readOnly=true) public RegistroEstudo buscar(Long id,String email){RegistroEstudo r=repository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Registro de estudo não encontrado"));tecnologias.buscar(r.getTecnologia().getId(),email);return r;}
    @Transactional(readOnly=true) public List<RegistroEstudo> listar(Long tecnologiaId,String email){tecnologias.buscar(tecnologiaId,email);
        return repository.findAllByTecnologiaIdOrderByDataDesc(tecnologiaId);}
    @Transactional public RegistroEstudo criar(RegistroEstudoRequestDTO r,String email){Tecnologia t=tecnologias.buscar(r.getTecnologiaId(),email);
        ConteudoPlanejado c=null;if(r.getConteudoId()!=null){c=conteudos.buscar(r.getConteudoId(),email);
            if(!c.getTecnologia().getId().equals(t.getId()))throw new BusinessException("Conteúdo não pertence à tecnologia informada");}
        return repository.save(new RegistroEstudo(t,c,r.getData(),r.getTipo(),r.getTempoMinutos(),r.getObservacoes()));}
    @Transactional(readOnly=true) public long minutos(Long tecnologiaId){return repository.somarMinutosPorTecnologia(tecnologiaId);}
}
