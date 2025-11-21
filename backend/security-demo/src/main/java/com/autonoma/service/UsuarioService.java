package com.autonoma.service;

import com.autonoma.dto.request.UsuarioRequest;
import com.autonoma.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse save(UsuarioRequest request);
    UsuarioResponse update(Integer id, UsuarioRequest request);
    List<UsuarioResponse> findAll();
    UsuarioResponse findById(Integer id);
    void delete(Integer id);
    UsuarioResponse restablecerContraseña(Integer id);
    UsuarioResponse desactivarUsuario(Integer id);
}
