package com.autonoma.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PersonalRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 40, message = "El nombre debe tener máximo 40 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$", message = "El nombre solo debe contener letras y espacios")
        String nombre,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 40, message = "El apellido paterno debe tener máximo 40 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$", message = "El apellido paterno solo debe contener letras y espacios")
        String apellidoPaterno,

        @NotBlank(message = "El apellido materno es obligatorio")
        @Size(max = 40, message = "El apellido materno debe tener máximo 40 caracteres")
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$", message = "El apellido materno solo debe contener letras y espacios")
        String apellidoMaterno,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^\\d{8}$", message = "El DNI debe tener exactamente 8 dígitos")
        String dni,

        @NotBlank(message = "El celular es obligatorio")
        @Pattern(regexp = "^\\d{9}$", message = "El celular debe tener exactamente 9 dígitos")
        String celular,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe tener formato válido")
        String correo,

        @Pattern(regexp = ".*\\.(png|jpg)$", message = "La imagen debe ser .png o .jpg")
        String urlimg
) {
}
