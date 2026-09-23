package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.exception.AssociadoNaoHabilitadoException;
import com.cooperativa.votacao.exception.ContabilizarVotosException;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.exception.SessaoFechadaException;
import com.cooperativa.votacao.exception.VotoDuplicadoException;
import com.cooperativa.votacao.integration.CpfValidationService;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.ResultadoVotacao;
import com.cooperativa.votacao.model.enums.TipoVoto;
import com.cooperativa.votacao.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VotoService {
    private static final Logger log = LoggerFactory.getLogger(VotoService.class);
    private final VotoRepository repository;
    private final PautaService pautaService;
    private final SessaoService sessaoService;
    private final CpfValidationService cpfValidationService;

    public VotoService(
            VotoRepository repository,
            PautaService pautaService,
            SessaoService sessaoService,
            CpfValidationService cpfValidationService)
    {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoService = sessaoService;
        this.cpfValidationService = cpfValidationService;
    }

    @Transactional
    public VotoResponse votarPauta(VotoRequest votoRequest) {
        log.info("Inicio no processo de votacao da Pauta {}", votoRequest.pautaId());

        if (!cpfValidationService.isEligibleToVote(votoRequest.associadoCpf())) {
            throw new AssociadoNaoHabilitadoException("Associado não está habilitado para votar (UNABLE_TO_VOTE).");
        }

        if (sessaoService.verificarSessaoFechada(votoRequest.pautaId())) {
            throw new SessaoFechadaException("Sessão de votação está encerrada.");
        }

        Pauta pautaRef = pautaService.gerarProxyPauta(votoRequest.pautaId());

        if(repository.existsByAssociadoIdAndPauta(votoRequest.associadoCpf(), pautaRef)) {
            throw new VotoDuplicadoException("Associado já votou nessa pauta.");
        }

        try {
            Voto voto = repository.save(votoRequest.toModel(pautaRef));
            log.info("Voto do associado na pauta {} registrado com sucesso.", voto.getPauta().getId());
            return VotoResponse.from(voto);
        } catch (Exception e) {
            log.error("Erro ao registrar voto do associado a pauta {}", votoRequest.pautaId());
            throw new ErroAoSalvarException("Erro ao salvar voto", e.getCause());
        }
    }

    public ResultadoVotacaoResponse contabilizarVotos(UUID pautaId) {
        try {
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
        } catch (Exception e) {
            log.error("Erro ao contabilizar votos da pauta {}", pautaId);
            throw new ContabilizarVotosException("Erro ao contabilizar votos.", e.getCause());
        }
    }
}
