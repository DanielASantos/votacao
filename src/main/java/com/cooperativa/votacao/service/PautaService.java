package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.PautaRequest;
import com.cooperativa.votacao.dto.response.PautaResponse;
import com.cooperativa.votacao.exception.RegistroNaoEncontradoException;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.enums.ResultadoVotacao;
import com.cooperativa.votacao.repository.PautaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PautaService {
    private static final Logger log = LoggerFactory.getLogger(PautaService.class);
    private final PautaRepository repository;

    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PautaResponse salvar(PautaRequest request) {
        try {
            Pauta pauta = repository.save(request.toModel());
            log.info("Pauta criada com sucesso: {}", pauta.getId());
            return PautaResponse.from(pauta);
        } catch (Exception e) {
            log.error("Erro ao criar Pauta");
            throw new ErroAoSalvarException("Erro ao salvar nova pauta", e.getCause());
        }
    }

    public PautaResponse buscarPorId(UUID id) {
        Pauta pauta = repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Pauta não encontrada."));
        return PautaResponse.from(pauta);
    }

    public Pauta gerarProxyPauta(UUID id) {
        return repository.getReferenceById(id);
    }

    @Transactional
    public Pauta alterarResultado(UUID pautaId, ResultadoVotacao resultadoVotacao) {
        Pauta pauta = repository.findById(pautaId).orElseThrow(() -> new RegistroNaoEncontradoException("Pauta não encontrada."));

        try {
            pauta.setResultadoVotacao(resultadoVotacao);
            log.info("Adicionado o status {} da votacao na Pauta {}", resultadoVotacao.name(), pautaId);
            return repository.save(pauta);
        } catch (Exception e) {
            log.error("Erro ao adicionar resultado da votacao na pauta {}", pautaId);
            throw new ErroAoSalvarException("Erro ao adicionar resultado da votacao na pauta", e.getCause());
        }
    }
}