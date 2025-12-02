package com.autonoma.service.impl;

import com.autonoma.dto.request.LoginRequest;
import com.autonoma.dto.response.LoginResponse;
import com.autonoma.exception.ResourceNotFoundException;
import com.autonoma.model.entity.Usuario;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.security.JwtService;
import com.autonoma.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.usuario(), request.contraseña())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Usuario usuario = usuarioRepository.findByUsuario(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        String token = jwtService.generateToken(userDetails);
        long expiresIn = jwtService.getExpirationMinutes();

        return new LoginResponse(
                token,
                expiresIn,
                usuario.getPersonal().getId(),
                usuario.getPersonal().getNombre(),
                usuario.getPersonal().getApellidoPaterno(),
                usuario.getRol().getNombre()
        );
    }
}
