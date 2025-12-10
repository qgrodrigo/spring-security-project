package com.autonoma.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRolRequest(

        @NotBlank(message = "El campo es obligatorio")
        Integer idRol
) {
}
