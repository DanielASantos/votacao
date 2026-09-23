package com.cooperativa.votacao.exception;

import com.cooperativa.votacao.dto.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroAoSalvarException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse erroAoSalvar(ErroAoSalvarException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse registroNaoEncontrado(RegistroNaoEncontradoException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(SessaoFechadaException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse sessaoFechada(SessaoFechadaException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(VotoDuplicadoException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse votoDuplicado(VotoDuplicadoException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(AssociadoNaoHabilitadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse associadoNaoHabilitado(AssociadoNaoHabilitadoException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(CpfInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse cpfInvalido(CpfInvalidoException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(IntegracaoExternaException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ExceptionResponse integracaoExterna(IntegracaoExternaException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(ContabilizarVotosException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ExceptionResponse contabilizarVotos(ContabilizarVotosException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }

    @ExceptionHandler(AbrirSessaoException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ExceptionResponse contabilizarVotos(AbrirSessaoException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getMessage(),
                request.getServletPath()
        );
    }
}
