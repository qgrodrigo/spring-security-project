package com.autonoma.dto.response;

public record MovimientoResponse(
        Integer id,
        Integer idUsuario,
        Integer idProducto,
        Integer cantidad,
        String fechaMovimiento,
        String tipoMovimiento
) {
}
