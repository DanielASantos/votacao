package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.SessaoRequest;
import com.cooperativa.votacao.dto.response.SessaoResponse;
import com.cooperativa.votacao.exception.RegistroNaoEncontradoException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Sessao;
import com.cooperativa.votacao.repository.SessaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessaoService {
    private final SessaoRepository repository;
    private final PautaService pautaService;

    public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
        this.repository = sessaoRepository;
        this.pautaService = pautaService;
    }

    @Transactional
    public SessaoResponse abrirSessao(SessaoRequest request) {

        Pauta pauta = pautaService.gerarProxyPauta(request.pautaId());

        int minutos = (request.tempoMinutos() != null && request.tempoMinutos() > 0) ? request.tempoMinutos() : 1;
        LocalDateTime dataFechamento = LocalDateTime.now().plusMinutes(minutos);

        Sessao sessao = new Sessao(pauta, LocalDateTime.now(), dataFechamento);
        Sessao nSessao = repository.save(sessao);

        return SessaoResponse.from(nSessao);
    }

    public SessaoResponse buscarPorId(UUID id) {
        Sessao sessao = repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Sessao não encontrada."));
        return SessaoResponse.from(sessao);
    }

    public Boolean verificarSessaoFechada(UUID pautaId) {
        Sessao sessao = repository.findByPautaId(pautaId);
        return LocalDateTime.now().isAfter(sessao.getDataFechamento());
    }
}
