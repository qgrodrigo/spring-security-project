package com.autonoma.dto.response;

import java.math.BigDecimal;

public record ProductoResponse(
        Integer id,
        String nombre,
        Integer stock,
        String color,
        String talla,
        BigDecimal precio,
        String fechaCreacion,
        String urlImg
) {
}
