package com.cooperativa.votacao.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SessaoRequest(

        @NotNull
        UUID pautaId,

        Integer tempoMinutos
) {

}
