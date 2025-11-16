package com.autonoma.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El usuario es oblitario")
        String usuario,
        @NotBlank(message = "La contraseña es obligtaria")
        String contraseña
) {
}
