package com.autonoma.dto;

public record UsuarioResponse(
        Integer id,
        String usuario,
        String correo,
        String rol,
        String estado,
        String contraseña,
        String nombreCompleto
) {}
