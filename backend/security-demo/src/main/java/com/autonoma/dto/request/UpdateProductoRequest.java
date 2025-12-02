package com.autonoma.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductoRequest(

        @NotNull(message = "El NOMBRE no puede estar vacío")
        @Size(max = 20, message = "NOMBRE debe tener menos de 20 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$", message = "El nombre solo debe contener letras")
        String nombre,

        @NotBlank(message = "El COLOR no puede estar vacío")
        @Size(max = 20, message = "COLOR debe tener menos de 20 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El color solo debe letras")
        String color,

        @NotNull(message = "La TALLA no puede estar vacía")
        @Pattern(regexp = "S|M|L|XL|XXL", message = "La talla debe ser S, M, L, XL o XXL")
        String talla,

        @NotNull(message = "El PRECIO no puede estar vacío")
        @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 2 decimales")
        BigDecimal precio,

        @Pattern(regexp = ".*\\.(png|jpg)$", message = "La imagen debe ser formato .png o .jpg")
        String urlImg
) {
}
