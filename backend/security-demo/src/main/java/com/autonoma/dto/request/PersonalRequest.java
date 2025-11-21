package com.autonoma.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PersonalRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El nombre solo debe contener letras")
        String nombre,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El apellido paterno solo debe contener letras")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es obligatorio")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+$", message = "El apellido materno solo debe contener letras")
        String apellidoMaterno,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
        String dni,

        @NotBlank(message = "El celular es obligatorio")
        @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener exactamente 9 dígitos")
        String celular,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener formato válido")
        //@Pattern(regexp = "^[\\w.-]+@gmail\\.com$", message = "El correo debe terminar en @gmail.com")
        String correo,

        String urlimg
) {
}
