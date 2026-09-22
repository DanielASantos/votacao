package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.Sessao;
import com.cooperativa.votacao.model.enums.StatusSessao;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessaoResponse(

       UUID id,
       String pauta,
       LocalDateTime dataAbertura,
       LocalDateTime dataFechamento,
       StatusSessao status

) {
    public static SessaoResponse from(Sessao sessao) {
        return new SessaoResponse(
                sessao.getId(),
                sessao.getPauta().getTitulo(),
                sessao.getDataAbertura(),
                sessao.getDataFechamento(),
                getStatus(sessao.getDataFechamento())
        );
    }

    private static StatusSessao getStatus(LocalDateTime dataFechamento) {
        if(LocalDateTime.now().isBefore(dataFechamento)) {
            return StatusSessao.ABERTA;
        }
        return StatusSessao.FECHADA;
    }

}
