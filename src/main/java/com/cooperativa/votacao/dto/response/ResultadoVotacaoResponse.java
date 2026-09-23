package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.enums.ResultadoVotacao;

import java.util.UUID;

public record ResultadoVotacaoResponse(
        UUID pautaId,
        Long totalSim,
        Long totalNao,
        ResultadoVotacao resultadoVotacao
) {
   public static ResultadoVotacaoResponse from(UUID pautaId, Long totalSim, Long totalNao, ResultadoVotacao resultadoVotacao) {
       return new ResultadoVotacaoResponse(
            pautaId,
            totalSim,
            totalNao,
            resultadoVotacao
       );
   }
}
