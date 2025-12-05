package com.autonoma.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record LogAuthResponse(
        Long id,
        LocalDate fecha,
        LocalTime hora,
        Integer idUsuario,
        String usuarioIngresado,
        String ipAddress,
        String tipoEvento
) {
}
