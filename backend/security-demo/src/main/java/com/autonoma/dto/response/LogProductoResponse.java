package com.autonoma.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record LogProductoResponse(
        Long id,
        LocalDate fecha,
        LocalTime hora,
        Integer idUsuario,
        String ipAddress,
        String tipoEvento,
        Integer idProducto,
        String productoNombre
) {
}
