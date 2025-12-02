package com.autonoma.dto.response;


public record LoginResponse(
        String token,
        long expiresIn,
        Integer idPersonal,
        String nombre,
        String apellido,
        String rol
) {}
