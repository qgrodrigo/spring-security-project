package com.autonoma.dto.request;

import com.autonoma.model.enums.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MovimientoRequest(

        @NotNull(message = "El IDPRODUCTO no debe ser nulo")
        @Positive(message = "El idProducto debe ser un número positivo")
        @Min(value = 1, message = "El IDPRODUCTO debe ser mayor a 0")
        Integer idProducto,

        @NotNull(message = "la CANTIDAD no debe ser nulo")
        @Min(value = 1, message = "La CANTIDAD debe ser mayor a 0")
        Integer cantidad,

        @NotNull(message = "El tipo de movimiento no debe ser nulo")
        //@Pattern(regexp = "ENTRADA|SALIDA", message = "El tipo de movimiento debe ser ENTRADA o SALIDA")
        TipoMovimiento tipoMovimiento
) {}
