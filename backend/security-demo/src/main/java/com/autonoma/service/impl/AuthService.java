package com.autonoma.service.impl;

import lombok.RequiredArgsConstructor;
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
