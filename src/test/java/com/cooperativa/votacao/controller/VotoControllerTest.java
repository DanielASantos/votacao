package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.service.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotoControllerTest {

    @Mock
    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    private UUID pautaId;

    @BeforeEach
    void setUp() {
        pautaId = UUID.randomUUID();
    }

    @Test
    void votarPauta_DeveRetornarStatusOkEResponse() {
        VotoRequest request = mock(VotoRequest.class);
        VotoResponse votoResponse = mock(VotoResponse.class);

        when(votoService.votarPauta(request)).thenReturn(votoResponse);

        ResponseEntity<VotoResponse> responseEntity = votoController.votarPauta(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(votoResponse, responseEntity.getBody());
        verify(votoService, times(1)).votarPauta(request);
    }

    @Test
    void contabilizarVotos_DeveRetornarStatusOkEResultado() {
        ResultadoVotacaoResponse resultadoResponse = mock(ResultadoVotacaoResponse.class);

        when(votoService.contabilizarVotos(pautaId)).thenReturn(resultadoResponse);

        ResponseEntity<ResultadoVotacaoResponse> responseEntity = votoController.contabilizarVotos(pautaId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(resultadoResponse, responseEntity.getBody());
        verify(votoService, times(1)).contabilizarVotos(pautaId);
    }
}
