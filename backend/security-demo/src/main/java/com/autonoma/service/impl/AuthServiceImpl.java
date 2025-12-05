package com.autonoma.service.impl;

import com.autonoma.config.IpUtils;
import com.autonoma.dto.request.LoginRequest;
import com.autonoma.dto.response.LoginResponse;
import com.autonoma.exception.*;
import com.autonoma.model.entity.Usuario;
import com.autonoma.model.enums.Estado;
import com.autonoma.model.enums.TipoEventoLogin;
import com.autonoma.repository.UsuarioRepository;
import com.autonoma.security.JwtService;
import com.autonoma.service.AuthService;
import com.autonoma.service.LogAuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final LogAuthService logAuthService;


    @Override
    public LoginResponse login(LoginRequest request, HttpServletRequest httpRequest) {

        String ip = IpUtils.getClientIp(httpRequest);

        Usuario usuario = usuarioRepository.findByUsuario(request.usuario()).orElse(null);
        if (usuario == null) {
            logAuthService.logAuth(null, request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
            throw new UsuarioNoExisteException("Usuario no existe.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.usuario(), request.contraseña())
            );

            if (usuario.getEstado() == Estado.BLOQUEADO) {
                throw new UsuarioBloqueadoException("Usuario bloqueado por múltiples intentos fallidos.");
            }

            if (usuario.getEstado() == Estado.INACTIVO) {
                throw new UsuarioInactivoException("Usuario inactivo.");
            }

            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);

            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            long expiresIn = jwtService.getExpirationMinutes();

            logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_SUCCESS);

            return new LoginResponse(
                    token,
                    expiresIn,
                    usuario.getPersonal().getId(),
                    usuario.getPersonal().getNombre(),
                    usuario.getPersonal().getApellidoPaterno(),
                    usuario.getRol().getNombre()
            );

        } catch (BadCredentialsException ex) {

            int intentos = usuario.getIntentosFallidos() + 1;
            usuario.setIntentosFallidos(intentos);

            if (intentos >= 3) {
                usuario.setEstado(Estado.BLOQUEADO);
                usuarioRepository.save(usuario);

                logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
                throw new UsuarioBloqueadoException("Usuario bloqueado por múltiples intentos fallidos.");
            }

            usuarioRepository.save(usuario);

            logAuthService.logAuth(usuario.getId(), request.usuario(), ip, TipoEventoLogin.LOGIN_FAILED);
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
    }

}
