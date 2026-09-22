package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessaoRepository extends JpaRepository<Sessao, UUID> {

    Sessao findByPautaId(UUID pautaId);
}
