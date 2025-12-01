package com.autonoma.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "El usuario es obligatorio")
        @Size(min = 5, max = 30, message = "El usuario debe tener entre 5 y 30 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
                message = "El usuario solo puede contener letras, números, puntos, guiones y guiones bajos")
        String usuario,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        @Pattern(
                regexp = ".*[a-z].*",
                message = "La contraseña debe contener al menos una letra minúscula"
        )
        String contraseña
) {
}
