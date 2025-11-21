package com.autonoma.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProductoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El nombre solo debe tener letras")
        String nombre,

        Integer stock,

        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El color solo debe letras")
        String color,

        String talla,


        String urlImg
) {
}
