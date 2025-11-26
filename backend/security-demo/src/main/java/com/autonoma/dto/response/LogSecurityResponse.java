package com.autonoma.dto.response;

import java.time.LocalDateTime;

public record LogSecurityResponse(
        Long id,
        String accion,
        String usuario,
        String ip,
        LocalDateTime fechaHora
) {
}
