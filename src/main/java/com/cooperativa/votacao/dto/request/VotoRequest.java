package com.cooperativa.votacao.dto.request;

import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.TipoVoto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record VotoRequest(

        @NotBlank @CPF
        String associadoCpf,

        @NotNull
        UUID pautaId,

        @NotNull
        TipoVoto voto
) {
    public Voto toModel(Pauta pauta) {
        return new Voto(
            pauta,
            associadoCpf,
            voto
        );
    }
}
