package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.PautaRequest;
import com.cooperativa.votacao.dto.response.PautaResponse;
import com.cooperativa.votacao.exception.RegistroNaoEncontradoException;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.enums.ResultadoVotacao;
import com.cooperativa.votacao.repository.PautaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PautaService {
    private final PautaRepository repository;

    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PautaResponse salvar(PautaRequest request) {
        try {
            Pauta pauta = repository.save(request.toModel());
            return PautaResponse.from(pauta);
        } catch (Exception e) {
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
        pauta.setResultadoVotacao(resultadoVotacao);
        return repository.save(pauta);
    }
}