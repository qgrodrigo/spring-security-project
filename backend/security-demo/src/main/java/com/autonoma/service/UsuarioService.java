package com.autonoma.service;

import com.autonoma.dto.request.UpdateRolRequest;
import com.autonoma.dto.request.UsuarioRequest;
import com.autonoma.dto.response.MessageResponse;
import com.autonoma.dto.response.UserResponse;
import com.autonoma.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse save(UsuarioRequest request);
    UsuarioResponse update(Integer id, UsuarioRequest request);
    List<UsuarioResponse> findAll();
    UsuarioResponse findById(Integer id);
    void delete(Integer id);
    UsuarioResponse restablecerContraseña(Integer id);
    MessageResponse activarUsuario(Integer id);
    MessageResponse desactivarUsuario(Integer id);
    UserResponse showUsuario(Integer id);

    MessageResponse cambiarRol(Integer id, UpdateRolRequest request);
}
