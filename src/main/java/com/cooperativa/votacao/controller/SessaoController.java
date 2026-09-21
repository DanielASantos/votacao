package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.SessaoRequest;
import com.cooperativa.votacao.dto.response.SessaoResponse;
import com.cooperativa.votacao.model.Sessao;
import com.cooperativa.votacao.service.SessaoService;
import jakarta.validation.Valid;
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
    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) { this.sessaoService = sessaoService; }

    @PostMapping
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest request) {
        Sessao sessao = sessaoService.abrirSessao(request);
        return ResponseEntity.status(HttpStatus.OK.value()).body(SessaoResponse.from(sessao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponse> buscarPorId(@PathVariable UUID id) {
        Sessao sessao = sessaoService.buscarPorId(id);
        return ResponseEntity.ok(SessaoResponse.from(sessao));
    }
}
