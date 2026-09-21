package com.cooperativa.votacao.dto.response;

import com.cooperativa.votacao.model.Sessao;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessaoResponse(

       UUID id,
       String pauta,
       LocalDateTime dataAbertura,
       LocalDateTime dataFechamento,
       String status

) {
    public static SessaoResponse from(Sessao sessao) {
        return new SessaoResponse(
                sessao.getId(),
                sessao.getPauta().getTitulo(),
                sessao.getDataAbertura(),
                sessao.getDataFechamento(),
                statusSessao(sessao.getDataFechamento())
        );
    }

    private static String statusSessao(LocalDateTime dataFechamento) {
        if(LocalDateTime.now().isBefore(dataFechamento)) {
            return "ABERTA";
        }
        return "FECHADA";
    }

}
