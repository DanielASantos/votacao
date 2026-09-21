package com.cooperativa.votacao.service;

import com.cooperativa.votacao.exception.PautaNaoEncontradaException;
import com.cooperativa.votacao.exception.SalvarPautaException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.repository.PautaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PautaService {
    private final PautaRepository repository;

    public PautaService(PautaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Pauta salvar(Pauta pauta) {
        try {
            return repository.save(pauta);
        } catch (Exception e) {
            throw new SalvarPautaException("Erro ao salvar nova pauta", e.getCause());
        }
    }

    public Pauta buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new PautaNaoEncontradaException("Pauta não encontrada."));
    }
}