package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotoRepository extends JpaRepository<Voto, UUID> {

    Long countByPautaIdAndVoto(UUID pautaId, TipoVoto tipoVoto);

    Boolean existsByAssociadoIdAndPauta(String associadoId, Pauta pauta);
}
