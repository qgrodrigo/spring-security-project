package com.autonoma.dto;

import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotNull(message = "El ID del personal es obligatorio")
        Integer idPersonal,

        @NotNull(message = "El ID del rol es obligatorio")
        Integer idRol
) {
}
