package com.autonoma.dto.response;

public record UsuarioResponse(
        Integer id,
        String usuario,
        String correo,
        String rol,
        String estado
        //String nombreCompleto
) {}
