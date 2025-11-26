package com.autonoma.dto.request;

import com.autonoma.model.enums.TipoMovimiento;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MovimientoRequest(

        @NotNull
        Integer idProducto,

        @NotNull
        @Min(1)
        Integer cantidad,

        @NotNull
        TipoMovimiento tipoMovimiento
) {}
