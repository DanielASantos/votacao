package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.service.VotoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {
    private static final Logger log = LoggerFactory.getLogger(VotoController.class);
    private final VotoService votoService;

    public VotoController(VotoService votoService) { this.votoService = votoService; }

    @PostMapping
    public ResponseEntity<VotoResponse>votarPauta(@RequestBody @Valid VotoRequest request) {
        log.info("Recebimento da requisicao para votacao da pauta {}", request.pautaId());
        return ResponseEntity.ok(votoService.votarPauta(request));
    }

    @GetMapping("/resultado/{pautaId}")
    public ResponseEntity<ResultadoVotacaoResponse>contabilizarVotos(@PathVariable UUID pautaId) {
        log.info("REcebimento da requisicao para contabilizar votos da pauta {}", pautaId);
        return ResponseEntity.ok(votoService.contabilizarVotos(pautaId));
    }
}
