package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.request.PlanoEstudoRequestDTO;
import com.thiagocosta.devmentor.backend.exception.*;
import com.thiagocosta.devmentor.backend.repository.PlanejamentoEstudoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PlanoEstudoService {
    private final PlanejamentoEstudoRepository repository; private final TecnologiaService tecnologias;
    public PlanoEstudoService(PlanejamentoEstudoRepository repository,TecnologiaService tecnologias){this.repository=repository;this.tecnologias=tecnologias;}
    @Transactional(readOnly=true) public PlanejamentoEstudo buscar(Long id,String email){PlanejamentoEstudo p=repository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Plano de estudo não encontrado"));tecnologias.buscar(p.getTecnologia().getId(),email);return p;}
    @Transactional(readOnly=true) public List<PlanejamentoEstudo> listar(Long tecnologiaId,String email){tecnologias.buscar(tecnologiaId,email);
        return repository.findAllByTecnologiaIdOrderByDataInicioDesc(tecnologiaId);}
    @Transactional(readOnly=true) public Optional<PlanejamentoEstudo> ativo(Long tecnologiaId,String email,LocalDate data){tecnologias.buscar(tecnologiaId,email);
        return repository.findAtivoByTecnologiaId(tecnologiaId,data);}
    @Transactional public PlanejamentoEstudo criar(PlanoEstudoRequestDTO r,String email){validar(r);Tecnologia t=tecnologias.buscar(r.getTecnologiaId(),email);
        if(repository.existsConflito(t.getId(),r.getDataInicio(),r.getDataFim(),null))throw new ConflictException("Já existe plano no período informado");
        return repository.save(new PlanejamentoEstudo(t,r.getDataInicio(),r.getDataFim(),r.getHorasPlanejadasTotais(),r.getHorasSemanais(),r.getObservacao()));}
    @Transactional public PlanejamentoEstudo atualizar(Long id,PlanoEstudoRequestDTO r,String email){validar(r);PlanejamentoEstudo p=buscar(id,email);
        if(!p.getTecnologia().getId().equals(r.getTecnologiaId()))throw new BusinessException("A tecnologia do plano não pode ser alterada");
        if(repository.existsConflito(r.getTecnologiaId(),r.getDataInicio(),r.getDataFim(),id))throw new ConflictException("Já existe plano no período informado");
        p.atualizar(r.getDataInicio(),r.getDataFim(),r.getHorasPlanejadasTotais(),r.getHorasSemanais(),r.getObservacao());return p;}
    public double percentualEsperado(PlanejamentoEstudo p,LocalDate hoje){if(hoje.isBefore(p.getDataInicio()))return 0;
        if(!hoje.isBefore(p.getDataFim()))return 100;long total=ChronoUnit.DAYS.between(p.getDataInicio(),p.getDataFim());
        long decorridos=ChronoUnit.DAYS.between(p.getDataInicio(),hoje);return total==0?100:(decorridos*100.0/total);}
    private void validar(PlanoEstudoRequestDTO r){if(!r.getDataFim().isAfter(r.getDataInicio()))
        throw new BusinessException("Data final deve ser posterior à data inicial");}
}
