package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.service.VotoService;
import jakarta.validation.Valid;
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
    private final VotoService votoService;

    public VotoController(VotoService votoService) { this.votoService = votoService; }

    @PostMapping
    public ResponseEntity<VotoResponse>votarPauta(@RequestBody @Valid VotoRequest request) {
        return ResponseEntity.ok(votoService.votarPauta(request));
    }

    @GetMapping("/resultado/{id}")
    public ResponseEntity<ResultadoVotacaoResponse>contabilizarVotos(@PathVariable UUID id) {
        return ResponseEntity.ok(votoService.contabilizarVotos(id));
    }
}
