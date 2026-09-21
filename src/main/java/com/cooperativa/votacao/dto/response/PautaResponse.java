package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.Pauta;

import java.util.UUID;

public record PautaResponse(

        UUID id,
        String titulo,
        String descricao
){
    public static PautaResponse from(Pauta pauta) {
        return  new PautaResponse(
                pauta.getId(),
                pauta.getTitulo(),
                pauta.getDescricao()
        );
    }
}
