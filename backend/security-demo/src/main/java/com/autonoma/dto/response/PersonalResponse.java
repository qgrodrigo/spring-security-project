package com.autonoma.dto.response;

import java.time.LocalDateTime;

public record PersonalResponse(
        Integer id,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String dni,
        String celular,
        String correo,
        String urlimg,
        LocalDateTime fechacreacion

) {}
