package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.SessaoRequest;
import com.cooperativa.votacao.dto.response.SessaoResponse;
import com.cooperativa.votacao.service.SessaoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessao")
public class SessaoController {
    private static final Logger log = LoggerFactory.getLogger(SessaoController.class);
    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) { this.sessaoService = sessaoService; }

    @PostMapping
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest request) {
        log.info("Recebimento da requisicao para abertura de sessao para pauta {}", request.pautaId());
        return ResponseEntity.status(HttpStatus.OK.value()).body(sessaoService.abrirSessao(request));
    }

    @GetMapping("/{sessaoId}")
    public ResponseEntity<SessaoResponse> buscarPorId(@PathVariable UUID sessaoId) {
        log.info("Recebimento da requisicao para buscar sessao {}.", sessaoId);
        return ResponseEntity.ok(sessaoService.buscarPorId(sessaoId));
    }
}
