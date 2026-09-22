package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.request.SessaoRequest;
import com.cooperativa.votacao.dto.response.SessaoResponse;
import com.cooperativa.votacao.service.SessaoService;
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
class SessaoControllerTest {

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private SessaoController sessaoController;

    private UUID sessaoId;

    @BeforeEach
    void setUp() {
        sessaoId = UUID.randomUUID();
    }

    @Test
    void abrirSessao_DeveRetornarStatusOkEResponse() {
        SessaoRequest request = mock(SessaoRequest.class);
        SessaoResponse sessaoResponse = mock(SessaoResponse.class);

        when(sessaoService.abrirSessao(request)).thenReturn(sessaoResponse);

        ResponseEntity<SessaoResponse> responseEntity = sessaoController.abrirSessao(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        assertEquals(sessaoResponse, responseEntity.getBody());
        verify(sessaoService, times(1)).abrirSessao(request);
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkEResponse() {
        SessaoResponse sessaoResponse = mock(SessaoResponse.class);

        when(sessaoService.buscarPorId(sessaoId)).thenReturn(sessaoResponse);

        ResponseEntity<SessaoResponse> responseEntity = sessaoController.buscarPorId(sessaoId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessaoResponse, responseEntity.getBody());
        verify(sessaoService, times(1)).buscarPorId(sessaoId);
    }
}
