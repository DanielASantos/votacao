package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.TipoVoto;

import java.util.UUID;

public record VotoResponse(

        UUID id,
        String pauta,
        String associadoId,
        TipoVoto voto
) {

    public static VotoResponse from(Voto voto) {
        return new VotoResponse(
                voto.getId(),
                voto.getPauta().getTitulo(),
                voto.getAssociadoId(),
                voto.getVoto()
        );
    }
}
