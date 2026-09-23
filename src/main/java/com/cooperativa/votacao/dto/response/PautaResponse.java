package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.enums.ResultadoVotacao;

import java.util.UUID;

public record PautaResponse(

        UUID id,
        String titulo,
        String descricao,
        ResultadoVotacao resultadoVotacao
){
    public static PautaResponse from(Pauta pauta) {
        return  new PautaResponse(
                pauta.getId(),
                pauta.getTitulo(),
                pauta.getDescricao(),
                pauta.getResultadoVotacao()
        );
    }
}
