package com.cooperativa.votacao.dto.response;

import java.time.LocalDateTime;

public record ExceptionResponse(

        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
