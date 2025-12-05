package com.autonoma.exception;

import org.springframework.security.core.AuthenticationException;

public class UsuarioBloqueadoException extends AuthenticationException {
    public UsuarioBloqueadoException(String msg) {
        super(msg);
    }

}
