package com.autonoma.security;


import com.autonoma.exception.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String message = "Error de autenticación.";

        if (authException instanceof UsuarioBloqueadoException) {
            message = "Usuario bloqueado por múltiples intentos fallidos.";
        } else if (authException instanceof UsuarioInactivoException) {
            message = "Usuario inactivo.";
        }else if (authException instanceof UsuarioNoExisteException) {
            message = "Usuario no existe.";
        } else if (authException instanceof CredencialesInvalidasException
                || authException instanceof BadCredentialsException) {
            message = "Usuario o contraseña incorrectos.";
        } else {
            message = "Token inválido o ausente.";
        }

        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}
