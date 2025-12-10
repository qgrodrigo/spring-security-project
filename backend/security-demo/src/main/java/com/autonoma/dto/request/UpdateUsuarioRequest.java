package com.autonoma.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUsuarioRequest(

        @NotBlank(message = "El campo es obligatorio")
        Integer idPersonal,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%*]).+$",
                message = "La contraseña debe contener al menos una minúscula, una mayúscula, un número y un carácter especial (@#$%*)"
        )

        @NotBlank(message = "El campo es obligatorio")
        String contraseña
) {
}
