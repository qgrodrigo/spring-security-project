package com.autonoma.service.impl;

import com.autonoma.model.entity.Personal;
import org.springframework.stereotype.Service;

@Service
public class UsuarioHelperService {

    // generate automatic user
    public String generarUsuario(Personal p) {
        String inicialNombre = p.getNombre().substring(0, 1).toUpperCase();
        String apellido = p.getApellidoPaterno().toLowerCase();
        return inicialNombre + apellido;
    }

    // generate automatic password
    public String generarContraseña(Personal p) {
        String inicialApellido = p.getApellidoPaterno().substring(0, 1).toUpperCase();
        String dni = p.getDni();
        String celular = p.getCelular();
        String inicialNombre = p.getNombre().substring(0, 1).toUpperCase();

        return inicialApellido +
                dni.substring(0, 3) +
                celular.substring(celular.length() - 3) +
                inicialNombre;
    }
}
