package com.cooperativa.votacao.dto.request;

import com.cooperativa.votacao.model.Pauta;
import jakarta.validation.constraints.NotBlank;

public record PautaRequest(

        @NotBlank
        String titulo,

        String descricao
) {
    public Pauta toModel() {
        return new Pauta(titulo, descricao);
    }
}