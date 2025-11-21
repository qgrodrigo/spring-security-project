package com.autonoma.dto.response;

public record ProductoResponse(
        Integer id,
        String nombre,
        Integer stock,
        String color,
        String talla,
        String fechaCreacion,
        String urlImg
) {
}
