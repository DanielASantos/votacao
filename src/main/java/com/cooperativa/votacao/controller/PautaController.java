package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.PautaRequest;
import com.cooperativa.votacao.dto.response.PautaResponse;
import com.cooperativa.votacao.service.PautaService;
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
@RequestMapping("/api/v1/pautas")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    public ResponseEntity<PautaResponse> criar(@RequestBody @Valid PautaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.salvar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pautaService.buscarPorId(id));
    }
}
