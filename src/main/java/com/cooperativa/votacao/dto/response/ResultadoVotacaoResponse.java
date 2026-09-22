package com.cooperativa.votacao.dto.response;

import java.util.UUID;

public record ResultadoVotacaoResponse(
        UUID pautaId,
        Long totalSim,
        Long totalNao
) {

}
