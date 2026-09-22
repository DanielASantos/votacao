package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.PautaRequest;
import com.cooperativa.votacao.dto.response.PautaResponse;
import com.cooperativa.votacao.service.PautaService;
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
class PautaControllerTest {

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private PautaController pautaController;

    private UUID pautaId;

    @BeforeEach
    void setUp() {
        pautaId = UUID.randomUUID();
    }

    @Test
    void criar_DeveRetornarStatusCreatedEResponse() {
        PautaRequest request = mock(PautaRequest.class);
        PautaResponse pautaResponse = mock(PautaResponse.class);

        when(pautaService.salvar(request)).thenReturn(pautaResponse);

        ResponseEntity<PautaResponse> responseEntity = pautaController.criar(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(pautaResponse, responseEntity.getBody());
        verify(pautaService, times(1)).salvar(request);
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkEResponse() {
        PautaResponse pautaResponse = mock(PautaResponse.class);

        when(pautaService.buscarPorId(pautaId)).thenReturn(pautaResponse);

        ResponseEntity<PautaResponse> responseEntity = pautaController.buscarPorId(pautaId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(pautaResponse, responseEntity.getBody());
        verify(pautaService, times(1)).buscarPorId(pautaId);
    }
}