package com.autonoma.service.impl;

import com.autonoma.dto.LoginRequest;
import com.autonoma.dto.LoginResponse;
import com.autonoma.model.Usuario;
import com.autonoma.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    /**
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    public LoginResponse login(LoginRequest request) {
        // Autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.usuario(),
                        request.contraseña()
                )
        );

        Usuario usuario = usuarioRepository.findByUsuario(request.usuario());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return new LoginResponse(
                usuario.getUsuario(),
                usuario.getRol().getNombre(),
                usuario.getEstado().name()
        );
    } **/
}
