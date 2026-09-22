package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.request.VotoRequest;
import com.cooperativa.votacao.dto.response.ResultadoVotacaoResponse;
import com.cooperativa.votacao.dto.response.VotoResponse;
import com.cooperativa.votacao.exception.ErroAoSalvarException;
import com.cooperativa.votacao.exception.SessaoFechadaException;
import com.cooperativa.votacao.exception.VotoDuplicadoException;
import com.cooperativa.votacao.model.Pauta;
import com.cooperativa.votacao.model.Voto;
import com.cooperativa.votacao.model.enums.TipoVoto;
import com.cooperativa.votacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    private VotoRepository repository;

    @Mock
    private PautaService pautaService;

    @Mock
    private SessaoService sessaoService;

    @InjectMocks
    private VotoService votoService;

    private UUID pautaId;
    private String cpfMock;
    private VotoRequest votoRequest;
    private Pauta pautaMock;
    private Voto votoMock;

    @BeforeEach
    void setUp() {
        pautaId = UUID.randomUUID();
        cpfMock = "12345678900";
        votoRequest = mock(VotoRequest.class);
        pautaMock = mock(Pauta.class);
        votoMock = mock(Voto.class);

        lenient().when(votoRequest.pautaId()).thenReturn(pautaId);
        lenient().when(votoRequest.associadoCpf()).thenReturn(cpfMock);
    }

    @Test
    void votarPauta_Sucesso_SalvaVoto() {
        when(sessaoService.verificarSessaoFechada(pautaId)).thenReturn(false);
        when(pautaService.gerarProxyPauta(pautaId)).thenReturn(pautaMock);
        when(repository.existsByAssociadoIdAndPauta(cpfMock, pautaMock)).thenReturn(false);
        when(votoRequest.toModel(pautaMock)).thenReturn(votoMock);
        when(repository.save(votoMock)).thenReturn(votoMock);

        try (MockedStatic<VotoResponse> responseMockedStatic = Mockito.mockStatic(VotoResponse.class)) {
            responseMockedStatic.when(() -> VotoResponse.from(votoMock)).thenReturn(mock(VotoResponse.class));

            VotoResponse result = votoService.votarPauta(votoRequest);

            assertNotNull(result);
            verify(repository, times(1)).save(votoMock);
        }
    }

    @Test
    void votarPauta_SessaoFechada_LancaExcecao() {
        when(sessaoService.verificarSessaoFechada(pautaId)).thenReturn(true);

        assertThrows(SessaoFechadaException.class, () -> votoService.votarPauta(votoRequest));

        verify(repository, never()).save(any());
    }

    @Test
    void votarPauta_VotoDuplicado_LancaExcecao() {
        when(sessaoService.verificarSessaoFechada(pautaId)).thenReturn(false);
        when(pautaService.gerarProxyPauta(pautaId)).thenReturn(pautaMock);
        when(repository.existsByAssociadoIdAndPauta(cpfMock, pautaMock)).thenReturn(true);

        assertThrows(VotoDuplicadoException.class, () -> votoService.votarPauta(votoRequest));

        verify(repository, never()).save(any());
    }

    @Test
    void votarPauta_ErroAoSalvar_LancaSalvarPautaException() {
        when(sessaoService.verificarSessaoFechada(pautaId)).thenReturn(false);
        when(pautaService.gerarProxyPauta(pautaId)).thenReturn(pautaMock);
        when(repository.existsByAssociadoIdAndPauta(cpfMock, pautaMock)).thenReturn(false);
        when(votoRequest.toModel(pautaMock)).thenReturn(votoMock);

        when(repository.save(votoMock)).thenThrow(new RuntimeException("DB error"));

        assertThrows(ErroAoSalvarException.class, () -> votoService.votarPauta(votoRequest));
    }

    @Test
    void contabilizarVotos_RetornaContagemCorreta() {
        when(repository.countByPautaIdAndVoto(pautaId, TipoVoto.SIM)).thenReturn(10L);
        when(repository.countByPautaIdAndVoto(pautaId, TipoVoto.NAO)).thenReturn(5L);

        ResultadoVotacaoResponse result = votoService.contabilizarVotos(pautaId);

        assertNotNull(result);
        verify(repository, times(1)).countByPautaIdAndVoto(pautaId, TipoVoto.SIM);
        verify(repository, times(1)).countByPautaIdAndVoto(pautaId, TipoVoto.NAO);
    }
}