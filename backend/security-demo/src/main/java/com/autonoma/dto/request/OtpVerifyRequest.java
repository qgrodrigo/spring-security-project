package com.autonoma.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OtpVerifyRequest(

        @NotBlank(message = "El usuario es obligatorio")
        Integer idUsuario,

        @NotBlank(message = "El usuario es obligatorio")
        String otp
) {}
