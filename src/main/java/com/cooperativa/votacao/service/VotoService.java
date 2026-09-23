package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.exception.SessaoFechadaException;
import com.cooperativa.votacao.exception.VotoDuplicadoException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.ResultadoVotacao;
import com.cooperativa.votacao.model.enums.TipoVoto;
import com.cooperativa.votacao.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VotoService {
    private final VotoRepository repository;
    private final PautaService pautaService;
    private final SessaoService sessaoService;

    public VotoService(
            VotoRepository repository,
            PautaService pautaService,
            SessaoService sessaoService)
    {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoService = sessaoService;
    }

    @Transactional
    public VotoResponse votarPauta(VotoRequest votoRequest) {

        if (sessaoService.verificarSessaoFechada(votoRequest.pautaId())) {
            throw new SessaoFechadaException("Sessão de votação está encerrada.");
        }

        Pauta pautaRef = pautaService.gerarProxyPauta(votoRequest.pautaId());

        if(repository.existsByAssociadoIdAndPauta(votoRequest.associadoCpf(), pautaRef)) {
            throw new VotoDuplicadoException("Associado já votou nessa pauta.");
        }

        try {
            Voto voto = repository.save(votoRequest.toModel(pautaRef));
            return VotoResponse.from(voto);
        } catch (Exception e) {
            throw new ErroAoSalvarException("Erro ao salvar voto", e.getCause());
        }
    }

    public ResultadoVotacaoResponse contabilizarVotos(UUID pautaId) {
        Long totalSim = repository.countByPautaIdAndVoto(pautaId, TipoVoto.SIM);
        Long totalNao = repository.countByPautaIdAndVoto(pautaId, TipoVoto.NAO);

        ResultadoVotacao resultado;
        if (totalSim.equals(totalNao)) {
            resultado = ResultadoVotacao.EMPATE;
            pautaService.alterarResultado(pautaId, resultado);
            return ResultadoVotacaoResponse.from(pautaId, totalSim, totalNao, resultado);
        }

        resultado = (totalSim > totalNao) ? ResultadoVotacao.ACEITA : ResultadoVotacao.REJEITADA;
        pautaService.alterarResultado(pautaId, resultado);
        return ResultadoVotacaoResponse.from(pautaId, totalSim, totalNao, resultado);
    }
}
