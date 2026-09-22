package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.SessaoRequest;
import com.cooperativa.votacao.dto.response.SessaoResponse;
import com.cooperativa.votacao.exception.RegistroNaoEncontradoException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Sessao;
import com.cooperativa.votacao.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoServiceTest {

    @Mock
    private SessaoRepository repository;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private SessaoService sessaoService;

    private UUID sessaoId;
    private UUID pautaId;
    private Sessao sessaoMock;

    @BeforeEach
    void setUp() {
        sessaoId = UUID.randomUUID();
        pautaId = UUID.randomUUID();
        sessaoMock = mock(Sessao.class);
    }

    @Test
    void abrirSessao_ComTempoMinutosDefinido_DeveCalcularDataCorretamente() {
        SessaoRequest request = mock(SessaoRequest.class);
        when(request.pautaId()).thenReturn(pautaId);
        when(request.tempoMinutos()).thenReturn(10);

        when(pautaService.gerarProxyPauta(pautaId)).thenReturn(mock(Pauta.class));
        when(repository.save(any(Sessao.class))).thenReturn(sessaoMock);

        try (MockedStatic<SessaoResponse> mockedStatic = Mockito.mockStatic(SessaoResponse.class)) {
            mockedStatic.when(() -> SessaoResponse.from(sessaoMock)).thenReturn(mock(SessaoResponse.class));

            SessaoResponse result = sessaoService.abrirSessao(request);

            assertNotNull(result);
            verify(repository, times(1)).save(any(Sessao.class));
        }
    }

    @Test
    void abrirSessao_TempoMinutosNulo_DeveAplicarPadraoDeUmMinuto() {
        SessaoRequest request = mock(SessaoRequest.class);
        when(request.pautaId()).thenReturn(pautaId);
        when(request.tempoMinutos()).thenReturn(null);

        when(pautaService.gerarProxyPauta(pautaId)).thenReturn(mock(Pauta.class));
        when(repository.save(any(Sessao.class))).thenReturn(sessaoMock);

        try (MockedStatic<SessaoResponse> mockedStatic = Mockito.mockStatic(SessaoResponse.class)) {
            mockedStatic.when(() -> SessaoResponse.from(sessaoMock)).thenReturn(mock(SessaoResponse.class));

            SessaoResponse result = sessaoService.abrirSessao(request);

            assertNotNull(result);
            verify(repository, times(1)).save(any(Sessao.class));
        }
    }

    @Test
    void buscarPorId_SessaoExistente_DeveRetornarResponse() {
        when(repository.findById(sessaoId)).thenReturn(Optional.of(sessaoMock));

        try (MockedStatic<SessaoResponse> mockedStatic = Mockito.mockStatic(SessaoResponse.class)) {
            mockedStatic.when(() -> SessaoResponse.from(sessaoMock)).thenReturn(mock(SessaoResponse.class));

            SessaoResponse result = sessaoService.buscarPorId(sessaoId);

            assertNotNull(result);
            verify(repository, times(1)).findById(sessaoId);
        }
    }

    @Test
    void buscarPorId_SessaoInexistente_DeveLancarRegistroNaoEncontradoException() {
        when(repository.findById(sessaoId)).thenReturn(Optional.empty());

        assertThrows(RegistroNaoEncontradoException.class, () -> sessaoService.buscarPorId(sessaoId));
        verify(repository, times(1)).findById(sessaoId);
    }

    @Test
    void verificarSessaoFechada_DataPassada_DeveRetornarTrue() {
        when(repository.findByPautaId(pautaId)).thenReturn(sessaoMock);
        when(sessaoMock.getDataFechamento()).thenReturn(LocalDateTime.now().minusMinutes(5));

        Boolean isFechada = sessaoService.verificarSessaoFechada(pautaId);

        assertTrue(isFechada);
        verify(repository, times(1)).findByPautaId(pautaId);
    }

    @Test
    void verificarSessaoFechada_DataFutura_DeveRetornarFalse() {
        when(repository.findByPautaId(pautaId)).thenReturn(sessaoMock);
        when(sessaoMock.getDataFechamento()).thenReturn(LocalDateTime.now().plusMinutes(5));

        Boolean isFechada = sessaoService.verificarSessaoFechada(pautaId);

        assertFalse(isFechada);
        verify(repository, times(1)).findByPautaId(pautaId);
    }
}