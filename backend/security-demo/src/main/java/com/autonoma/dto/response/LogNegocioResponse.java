package com.autonoma.dto.response;

import java.time.LocalDateTime;

public record LogNegocioResponse(
        Long id,
        String accion,
        String tabla,
        Long idUsuario,
        LocalDateTime fechaHora,
        String detalles
) {
}
