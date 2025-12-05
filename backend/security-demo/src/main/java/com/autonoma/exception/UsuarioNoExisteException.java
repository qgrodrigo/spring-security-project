package com.autonoma.exception;

import org.springframework.security.core.AuthenticationException;

public class UsuarioNoExisteException extends AuthenticationException {

    public UsuarioNoExisteException(String msg) {
        super(msg);
    }
}
