package com.autonoma.dto;

public record LoginResponse(
        String usuario,
        String rol,
        String estado
) {}
