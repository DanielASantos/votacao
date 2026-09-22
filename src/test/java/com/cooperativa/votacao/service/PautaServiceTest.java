package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.PautaRequest;
import com.cooperativa.votacao.dto.response.PautaResponse;
import com.cooperativa.votacao.exception.RegistroNaoEncontradoException;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository repository;

    @InjectMocks
    private PautaService pautaService;

    private UUID pautaId;
    private Pauta pautaMock;
    private PautaRequest requestMock;

    @BeforeEach
    void setUp() {
        pautaId = UUID.randomUUID();
        pautaMock = mock(Pauta.class);
        requestMock = mock(PautaRequest.class);
    }

    @Test
    void salvar_ComSucesso_RetornaPautaResponse() {
        when(requestMock.toModel()).thenReturn(pautaMock);
        when(repository.save(any(Pauta.class))).thenReturn(pautaMock);

        try (MockedStatic<PautaResponse> responseMockedStatic = Mockito.mockStatic(PautaResponse.class)) {
            PautaResponse responseEsperada = mock(PautaResponse.class);
            responseMockedStatic.when(() -> PautaResponse.from(pautaMock)).thenReturn(responseEsperada);

            PautaResponse result = pautaService.salvar(requestMock);

            assertNotNull(result);
            verify(repository, times(1)).save(any(Pauta.class));
        }
    }

    @Test
    void salvar_ComErro_LancaSalvarPautaException() {
        when(requestMock.toModel()).thenReturn(pautaMock);
        when(repository.save(any(Pauta.class))).thenThrow(new RuntimeException("Database error"));

        ErroAoSalvarException exception = assertThrows(ErroAoSalvarException.class, () -> {
            pautaService.salvar(requestMock);
        });

        assertEquals("Erro ao salvar nova pauta", exception.getMessage());
    }

    @Test
    void buscarPorId_Existente_RetornaPautaResponse() {
        when(repository.findById(pautaId)).thenReturn(Optional.of(pautaMock));

        try (MockedStatic<PautaResponse> responseMockedStatic = Mockito.mockStatic(PautaResponse.class)) {
            PautaResponse responseEsperada = mock(PautaResponse.class);
            responseMockedStatic.when(() -> PautaResponse.from(pautaMock)).thenReturn(responseEsperada);

            PautaResponse result = pautaService.buscarPorId(pautaId);

            assertNotNull(result);
            verify(repository, times(1)).findById(pautaId);
        }
    }

    @Test
    void buscarPorId_Inexistente_LancaPautaNaoEncontradaException() {
        when(repository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(RegistroNaoEncontradoException.class, () -> pautaService.buscarPorId(pautaId));
    }

    @Test
    void gerarProxyPauta_RetornaReferencia() {
        when(repository.getReferenceById(pautaId)).thenReturn(pautaMock);

        Pauta result = pautaService.gerarProxyPauta(pautaId);

        assertNotNull(result);
        assertEquals(pautaMock, result);
    }
}